package cn.hoxise.system.biz.convert;

import cn.hoxise.system.api.log.dto.OperateLogCreateReqDTO;
import cn.hoxise.system.biz.dal.entity.OperateLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author hoxise
 * @Description: 操作日志映射
 * @Date 2026-01-04 上午7:34
 */
@Mapper
public interface OperateLogConvert {

    OperateLogConvert INSTANCE = Mappers.getMapper(OperateLogConvert.class);

    OperateLogDO convert(OperateLogCreateReqDTO reqDTO);

}
