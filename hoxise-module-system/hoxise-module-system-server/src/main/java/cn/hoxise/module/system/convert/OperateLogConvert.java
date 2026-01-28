package cn.hoxise.module.system.convert;

import cn.hoxise.module.system.api.log.dto.OperateLogCreateReqDTO;
import cn.hoxise.module.system.dal.entity.OperateLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * OperateLogConvert
 *
 * @author hoxise
 * @since 2026/01/14 06:08:22
 */
@Mapper
public interface OperateLogConvert {

    OperateLogConvert INSTANCE = Mappers.getMapper(OperateLogConvert.class);

    OperateLogDO convert(OperateLogCreateReqDTO reqDTO);

}
