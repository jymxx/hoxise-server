package cn.hoxise.common.base.utils.encrypt;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @Author: hoxise
 * @Description: 统计封装加密方法
 * @Date: 2023/8/27 1:59
 */
public class EncryptUtil {


    /***
     * @Description: hutool包的MD5工具
     * @param str
     * @return: java.lang.String
     * @author: hoxise
     * @date: 2023/8/27 2:01
     */
    public static String md5(String str){
        return DigestUtil.md5Hex(str);
    }
}
