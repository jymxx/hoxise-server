package cn.hoxise.self.biz.service.ai;

import cn.hoxise.self.biz.dal.entity.AiRequestRecordDO;
import cn.hoxise.self.biz.pojo.enums.AiMethodEnum;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Hoxise
* @description 针对表【ai_request_record】的数据库操作Service
* @createDate 2025-12-25 15:05:41
*/
public interface AiRequestRecordService extends IService<AiRequestRecordDO> {

    /**
     * @description: 记录调用日志
     * @author: hoxise
     * @date: 2025/12/25 下午3:14
     */
    void record(AiMethodEnum method,Long userid);

    void aiRateLimit(Long userid);
}
