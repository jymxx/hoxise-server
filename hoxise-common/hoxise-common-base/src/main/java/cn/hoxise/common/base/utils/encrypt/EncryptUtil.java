package cn.hoxise.common.base.utils.encrypt;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * EncryptUtil 加密工具
 *
 * @author hoxise
 * @since 2026/01/14 06:50:19
 */
public class EncryptUtil {


    /**
     * md5
     *
     * @param str 字符串
     * @return 加密后字符串
     * @author hoxise
     * @since 2026/01/14 06:50:27
     */
    public static String md5(String str){
        return DigestUtil.md5Hex(str);
    }
}
