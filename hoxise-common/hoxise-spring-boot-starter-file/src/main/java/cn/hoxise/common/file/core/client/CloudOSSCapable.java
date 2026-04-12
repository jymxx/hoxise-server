package cn.hoxise.common.file.core.client;

import java.util.List;

/**
 * 云存储高级能力接口：支持预签名 URL 和分片上传
 *
 * @author hoxise
 * @since 2026/04/10
 */
public interface CloudOSSCapable {

    /**
     * 生成预签名 URL（用于前端直传或临时访问） 默认一小时
     *
     * @param objectName 对象名称
     * @return 预签名 URL
     */
    String generatePresignedUrl(String objectName);


}
