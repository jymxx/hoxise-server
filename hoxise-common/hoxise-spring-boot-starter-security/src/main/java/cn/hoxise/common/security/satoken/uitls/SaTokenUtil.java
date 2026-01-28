package cn.hoxise.common.security.satoken.uitls;

import cn.dev33.satoken.stp.StpUtil;

/**
 * sa-token框架简单工具 文档地址https://sa-token.cc/
 * 考虑到可能会换成shiro或spring security 方便替换
 * @author hoxise
 * @since 2026/01/14 06:24:37
 */

public class SaTokenUtil{

    /** 登录 */
    public static String login(long userid) {
        StpUtil.login(userid);
        // 获取当前会话的 token 值
        return StpUtil.getTokenValue();
    }

    /** 登出 */
    public static void logout() {
        StpUtil.logout();
    }

    /** 检查是否已登录 */
    public static boolean isLogin() {
        return StpUtil.isLogin();
    }

    /** 获取登录ID */
    public static Object getLoginId() {
        return StpUtil.getLoginId();
    }

    /** 获取登录ID（转为long类型）*/
    public static long getLoginIdAsLong() {
        return StpUtil.getLoginIdAsLong();
    }

    /** 获取当前token */
    public static String getTokenValue() {
        return StpUtil.getTokenValue();
    }

    /**
     * 获取当前登录用户的ID，如果未登录则返回null
     */
    public static Object getLoginIdDefaultNull(){
        return StpUtil.getLoginIdDefaultNull();
    }

    /**
     * 根据token值获取对应的登录用户ID
     * @param tokenValue token值
     * @return 登录用户ID
     */
    public static Object getLoginIdByToken(String tokenValue) {
        return StpUtil.getLoginIdByToken(tokenValue);
    }

    /**
     * 检查当前登录用户是否具有指定角色，如果不具有该角色则抛出异常
     * @param role 需要检查的角色名称
     */
    public static void checkRole(String role) {
        StpUtil.checkRole(role);
    }



}
