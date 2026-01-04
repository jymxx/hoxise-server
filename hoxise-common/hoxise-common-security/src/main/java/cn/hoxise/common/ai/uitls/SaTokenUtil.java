package cn.hoxise.common.ai.uitls;

import cn.dev33.satoken.stp.StpUtil;

/**
 * @Author: hoxise
 * @Description: sa-token框架工具 文档地址 https://sa-token.cc/
 * @Date: 2024/1/18 21:40
 */
public class SaTokenUtil {

    /** 登录 */
    public static String login(long userid) {
        StpUtil.login(userid);
        // 获取当前会话的 token 值
        return StpUtil.getTokenValue();
    }

    /** 当前会话注销登录 */
    public static void logout() {
        StpUtil.logout();
    }

    /** 获取当前会话登录ID */
    public static Long getLoginId(){
        return StpUtil.getLoginIdAsLong();
    }

    /** 获取当前会话登录ID 默认null*/
    public static Long getLoginIdOrNull(){
        Object loginIdDefaultNull = StpUtil.getLoginIdDefaultNull();
        return loginIdDefaultNull == null ? null : (Long) loginIdDefaultNull;
    }
    /** 获取当前会话是否已经登录，返回true=已登录，false=未登录 */
    public static boolean isLogin() {
        return StpUtil.isLogin();
    }

    /** 检验当前会话是否已经登录, 如果未登录，则抛出异常：`NotLoginException` */
    public static void checkLogin() {
        StpUtil.checkLogin();
    }

}
