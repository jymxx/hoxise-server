package cn.hoxise.system.biz.service.user;


import cn.hoxise.system.biz.controller.user.vo.UserInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.hoxise.system.biz.dal.entity.SystemUserDO;

/**
* @author 永远的十七岁
* @description 针对表【tb_sys_user】的数据库操作Service
* @createDate 2023-08-27 00:15:59
*/
public interface SystemUserService extends IService<SystemUserDO> {

    /**
     * @Description: 根据用户名获取用户
     * @param username
     * @return: com.hoxise.general.entity.SysUser
     * @author: hoxise
     * @date: 2023/8/27 2:04
     */
    SystemUserDO queryByUsername(String username);

    SystemUserDO queryByPhoneNumber(String phoneNumber);

    UserInfoVO getUserInfo();

    //注册
    SystemUserDO register(String phoneNumber);
}
