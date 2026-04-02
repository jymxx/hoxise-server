package cn.hoxise.module.system.convert;

import cn.hoxise.module.system.api.user.dto.UserInfoRespDTO;
import cn.hoxise.module.system.controller.user.vo.UserInfoVO;
import cn.hoxise.module.system.controller.user.vo.UserInfoVO;
import cn.hoxise.module.system.dal.entity.SystemUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * SystemUserConvert
 *
 * @author hoxise
 * @since 2026/01/14 06:08:27
 */
@Mapper
public interface SystemUserConvert {

    SystemUserConvert INSTANCE = Mappers.getMapper(SystemUserConvert.class);

    UserInfoVO convert(SystemUserDO systemUserDO);

    UserInfoRespDTO convertToInfoRespDTO(SystemUserDO systemUserDO);
}
