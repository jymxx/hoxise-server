package cn.hoxise.self.ai.service;


import cn.hoxise.self.ai.dal.entity.AiRequestRecordDO;
import cn.hoxise.self.ai.pojo.enums.AiMethodEnum;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * AiRequestRecordService
 *
 * @author Hoxise
 * @since 2026/01/14 14:52:22
 */
public interface AiRequestRecordService extends IService<AiRequestRecordDO> {

    /**
     * 记录请求日志
     *
     * @param method 方法名称
     * @param userid 请求用户id
     * @author hoxise
     * @since 2026/01/14 14:52:26
     */
    void record(AiMethodEnum method, Long userid);

    /**
     * 限制用户请求频率
     *
     * @param userid 用户id
     * @author hoxise
     * @since 2026/01/14 14:52:26
     */
    void aiRateLimit(Long userid);
}
