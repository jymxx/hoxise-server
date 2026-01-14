package cn.hoxise.system.biz.convert;

import cn.hoxise.system.api.log.dto.OperateLogCreateReqDTO;
import cn.hoxise.system.biz.dal.entity.OperateLogDO;
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
