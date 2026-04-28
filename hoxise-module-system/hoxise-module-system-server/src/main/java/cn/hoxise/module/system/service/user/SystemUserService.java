package cn.hoxise.module.system.service.user;


import cn.hoxise.module.system.controller.user.dto.ModifyUserInfoDTO;
import cn.hoxise.module.system.controller.user.vo.UserInfoVO;
import cn.hoxise.module.system.dal.entity.SystemUserDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * SystemUserService
 *
 * @author 永远的十七岁
 * @since 2026/01/14 05:56:58
 */
public interface SystemUserService extends IService<SystemUserDO> {

    /**
     * 根据用户名称查询用户信息
     *
     * @param username 用户名称
     * @return 用户DO
     * @author hoxise
     * @since 2026/01/14 05:57:15
     */
    SystemUserDO queryByUsername(String username);

    /**
     * 根据用户手机号查询用户信息
     *
     * @param phoneNumber 手机号
     * @return 用户DO
     * @author hoxise
     * @since 2026/01/14 05:58:43
     */
    SystemUserDO queryByPhoneNumber(String phoneNumber);

    /**
     * 获取用户信息 根据token
     *
     * @return 获取用户信息
     * @author hoxise
     * @since 2026/01/14 05:59:30
     */
    UserInfoVO getUserInfo();


    /**
     * register 注册
     *
     * @param phoneNumber 手机号
     * @return SystemUserDO
     * @author hoxise
     * @since 2026/01/14 06:00:00
     */
    SystemUserDO register(String phoneNumber);

    /**
     * 修改用户信息
     *
     * @param dto 修改用户信息
     * @author hoxise
     * @since 2026/04/02 10:14:05
     */
    void modifyUserInfo(ModifyUserInfoDTO dto);

    /**
     * 上传用户头像
     *
     * @param file 头像文件
     * @return 头像访问地址
     * @author hoxise
     * @since 2026/04/02
     */
    String uploadAvatar(MultipartFile file);

}
