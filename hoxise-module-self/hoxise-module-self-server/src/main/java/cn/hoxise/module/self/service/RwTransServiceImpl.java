package cn.hoxise.module.self.service;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.common.base.utils.date.DateUtil;
import cn.hoxise.common.base.utils.servlet.ServletUtil;
import cn.hoxise.common.redis.utils.RedisUtil;
import cn.hoxise.module.ai.api.ChatApi;
import cn.hoxise.module.self.controller.dto.RwTransAcceptFileDTO;
import cn.hoxise.module.self.controller.dto.RwTransPreCheckDTO;
import cn.hoxise.module.self.controller.dto.RwTransStartDTO;
import cn.hoxise.module.self.mq.message.RwTransMessage;
import cn.hoxise.module.self.mq.producer.RwTransProducer;
import cn.hoxise.module.self.pojo.bo.RwSubTransBO;
import cn.hoxise.module.self.pojo.bo.RwTransBO;
import cn.hoxise.module.self.pojo.constants.RedisConstants;
import cn.hoxise.module.self.pojo.constants.RwConstants;
import cn.hoxise.module.self.pojo.enums.RwTransStatus;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 环世界
 *
 * @author hoxise
 * @since 2026/2/3 上午9:41
 */
@Service
@Slf4j
public class RwTransServiceImpl implements RwTransService {

    @Value("${hoxise.temp-dir}")
    private String tempDir;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RwTransProducer rwTransProducer;

    @Resource
    private ChatApi chatApi;

    @Override
    public String preCheck(RwTransPreCheckDTO dto){
        if (dto.getPaths().size() > 30){
            throw new ServiceException("文件数量限制30个以内");
        }
        dto.getPaths().forEach(path -> {
            if (!path.startsWith("/Languages")){
                throw new ServiceException("请确保只上传Languages文件夹");
            }
        });
        List<String> filterPath = dto.getPaths().stream().filter(path -> path.startsWith("/Languages/ChineseSimplified") && path.endsWith(".xml")).toList();

        //生成预检key
        String preCheckId = UUID.randomUUID().toString();
        //保存预检查信息
        //将redis当作数据库，这里就是存了一条数据, 一小时后过期
        RwTransBO build = RwTransBO.builder()
                .preCheckId(preCheckId)
                .paths(filterPath).build();
        saveTransBO(preCheckId, build);
        return preCheckId;
    }

    @Override
    public void acceptFile(RwTransAcceptFileDTO dto){
        //验证文件能不能上传
        RwTransBO rwTransBO = getAndCheckTransBO(dto.getPreCheckId());
        //过滤无效的内容
        if (!rwTransBO.getPaths().contains(dto.getPath())
            || !Objects.requireNonNull(dto.getFile().getOriginalFilename()).endsWith(".xml")){
            throw new ServiceException("无效文件");
        }
        try{
            //上传目录 → 临时目录/接口工作目录/日期/预检查ID
            String folder = tempDir + File.separator + RwConstants.RIM_WORLD_UPLOAD_DIR + File.separator + LocalDate.now().format(DateUtil.DATE_FORMATTER)
                     + File.separator + dto.getPreCheckId();
            if (!FileUtil.exist(folder)){
                FileUtil.mkdir(folder);
            }
            //保存文件到主机
            String flePath = folder + File.separator + dto.getPath() + File.separator + dto.getFile().getOriginalFilename();
            FileUtil.writeFromStream(dto.getFile().getInputStream(), flePath);

            //保存记录
            RwSubTransBO build = RwSubTransBO.builder()
                    .preCheckId(dto.getPreCheckId())
                    .localPath(flePath)
                    .status(RwTransStatus.check)
                    .build();
            saveSubTransBO(dto.getPreCheckId(),dto.getPath(), build);
        } catch (IOException e) {
            log.error("RimWorld trans文件上传失败: {}", e.getMessage());
            throw new ServiceException("文件上传失败: " + e.getMessage());
        }

    }

    @Override
    @SneakyThrows
    public void startTrans(RwTransStartDTO dto){
        rateLimit();//限流
        //锁
        RLock lock = redissonClient.getLock(RedisConstants.RW_TRANS_RESULT_LOCK_KEY + dto.getPreCheckId());
        if (!lock.tryLock(10,1, TimeUnit.HOURS)){
            throw new ServiceException("已经开始翻译,请勿重复提交");
        }

        RwTransBO rwTransBO = getAndCheckTransBO(dto.getPreCheckId());
        //先全部校验一遍
        for (String path : rwTransBO.getPaths()) {
            getAndCheckSubTransBO(dto.getPreCheckId(), path);
        }
        //循环发送MQ消息
        for (String path : rwTransBO.getPaths()) {
            RwSubTransBO rwSubTransBO = getAndCheckSubTransBO(dto.getPreCheckId(), path);
            //异步发送MQ消息
            RwTransMessage message = RwTransMessage.builder()
                    .preCheckId(dto.getPreCheckId())
                    .path(path)
                    .localPath(rwSubTransBO.getLocalPath())
                    .author(dto.getAuthor())
                    .packageId(dto.getPackageId())
                    .userPrompt(dto.getUserPrompt())
                    .build();
            rwTransProducer.asyncSendTransMessage(message);
        }


    }

    @Override
    public List<RwSubTransBO> getTransList(String preCheckId){
        RwTransBO rwTransBO = getAndCheckTransBO(preCheckId);
        return new ArrayList<>(rwTransBO.getPaths().stream().map(path -> getAndCheckSubTransBO(preCheckId, path)).toList());

    }

    @Override
    public String getTransPrompt(){
        return """
                你是一位专业的游戏本地化专家，负责将RimWorld的汉化Mod内容翻译成简体中文。
                请翻译以下xml文件内容，严格保持原有格式回复，使我能直接替换内容:
        
        """;
    }

    @Override
    public void handleTrans(RwTransMessage message){
        String localPath = message.getLocalPath();
        
        //翻译中状态
        RwSubTransBO subTransBO = getAndCheckSubTransBO(message.getPreCheckId(), localPath);
        subTransBO.setStatus(RwTransStatus.translating);
        saveSubTransBO(message.getPreCheckId(), localPath, subTransBO);

        //读取文件内容
        String xmlContent = FileUtil.readUtf8String(localPath);
        String transRes = chatApi.chat(getTransPrompt(), message.getUserPrompt() + xmlContent);

        //保存为新文件
        String folder = message.getLocalPath() + File.separator + "res";
        if (!FileUtil.exist(folder)){
            FileUtil.mkdir(folder);
        }
        String fullPath = folder + File.separator + message.getPath();
        FileUtil.writeUtf8String(transRes, fullPath);

        //翻译完成状态
        subTransBO.setStatus(RwTransStatus.complete);
        saveSubTransBO(message.getPreCheckId(), localPath, subTransBO);

    }

    /**
     * 限流
     */
    private void rateLimit(){
        String clientIp = ServletUtil.getClientIp(Objects.requireNonNull(ServletUtil.getRequest()));
        String key = RedisConstants.RW_IP_LIMIT_KEY + clientIp;
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        rateLimiter.trySetRate(RateType.OVERALL, 100, Duration.ofDays(1));
        if (!rateLimiter.tryAcquire()){
            throw new RuntimeException("同IP每天限制调用100次");
        }
    }


    //保存BO
    private void saveTransBO(String preCheckId, RwTransBO rwTransBO){
        redisUtil.setObject(RedisConstants.RW_TABLE_TRANS_KEY + preCheckId, rwTransBO, 6, TimeUnit.HOURS);

    }

    //获取BO
    private RwTransBO getAndCheckTransBO(String preCheckId){
        String preCheckRedisKey = RedisConstants.RW_TABLE_TRANS_KEY + preCheckId;
        if (StrUtil.isBlank(preCheckId) || !redisUtil.hasKey(preCheckRedisKey)){
            throw new ServiceException("无效key");
        }
        return (RwTransBO) redisUtil.getObject(preCheckRedisKey);
    }

    //保存子BO
    private void saveSubTransBO(String preCheckId, String path, RwSubTransBO rwSubTransBO){
        redisUtil.setObject(RedisConstants.RW_SUB_TABLE_TRANS_KEY + preCheckId + "::" + path, rwSubTransBO,6, TimeUnit.HOURS);
    }

    //获取子BO
    private RwSubTransBO getAndCheckSubTransBO(String preCheckId, String path){
        String subPreCheckRedisKey = RedisConstants.RW_SUB_TABLE_TRANS_KEY + preCheckId + "::" + path;
        if (!redisUtil.hasKey(subPreCheckRedisKey)){
            throw new ServiceException("无效key");
        }
        return (RwSubTransBO) redisUtil.getObject(subPreCheckRedisKey);
    }
}
