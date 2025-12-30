package cn.hoxise.system.biz.convert;

import cn.hoxise.system.biz.controller.user.vo.UserInfoVO;
import cn.hoxise.system.biz.dal.entity.SystemUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author hoxise
 * @Description: 用户转换类
 * @Date 2025-12-30 上午3:38
 */
@Mapper
public interface SystemUserConvert {

    SystemUserConvert INSTANCE = Mappers.getMapper(SystemUserConvert.class);

    UserInfoVO convert(SystemUserDO systemUserDO);
}
