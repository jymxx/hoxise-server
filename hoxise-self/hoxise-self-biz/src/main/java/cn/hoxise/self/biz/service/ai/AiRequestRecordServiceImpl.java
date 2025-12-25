package cn.hoxise.self.biz.service.ai;

import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.self.biz.pojo.enums.AiMethodEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.self.biz.dal.entity.AiRequestRecordDO;
import cn.hoxise.self.biz.dal.mapper.AiRequestRecordMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
* @author Hoxise
* @description 针对表【ai_request_record】的数据库操作Service实现
* @createDate 2025-12-25 15:05:41
*/
@Service
public class AiRequestRecordServiceImpl extends ServiceImpl<AiRequestRecordMapper, AiRequestRecordDO>
    implements AiRequestRecordService{


    @Override
    public void record(AiMethodEnum method, Long loginId) {
        AiRequestRecordDO recordDO = AiRequestRecordDO.builder()
                .userid(loginId)
                .method(method)
                .requestTime(LocalDateTime.now())
                .build();
        this.save(recordDO);
    }
}




