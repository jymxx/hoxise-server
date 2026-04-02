package cn.hoxise.module.system.api.user;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.module.system.api.user.dto.UserInfoRespDTO;
import cn.hoxise.module.system.convert.SystemUserConvert;
import cn.hoxise.module.system.dal.entity.SystemUserDO;
import cn.hoxise.module.system.service.user.SystemUserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户api
 *
 * @author hoxise
 * @since 2026/4/2 下午11:37
 */
@RestController
public class SystemUserApiImpl implements SystemUserApi{

    @Resource
    private SystemUserService systemUserService;

    @Override
    public CommonResult<UserInfoRespDTO> getUserById(Long userId) {
        SystemUserDO one = systemUserService.getById(userId);
        return CommonResult.success(SystemUserConvert.INSTANCE.convertToInfoRespDTO(one));
    }

}
