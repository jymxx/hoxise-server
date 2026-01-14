package cn.hoxise.system.biz.convert;

import cn.hoxise.system.biz.controller.user.vo.UserInfoVO;
import cn.hoxise.system.biz.dal.entity.SystemUserDO;
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
}
