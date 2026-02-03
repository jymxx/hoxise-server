package cn.hoxise.common.file.core.client.impl;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.common.file.core.config.FileStorageProperties;
import cn.hoxise.common.file.core.pojo.FileStorageDTO;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * 阿里云OSS
 *
 * @author hoxise
 * @since 2026/01/14 06:53:19
 */
@Slf4j
public class AliyunOssClient extends AbstractFileClient {

    private OSS ossClient;

    public AliyunOssClient(FileStorageProperties.AliyunOssConfig clientProperties) {
        super(clientProperties);
    }

    @Override
    protected void doInit() {
        log.info("----初始化AliyunOss连接配置----");
        // 阿里云OSS配置
        FileStorageProperties.AliyunOssConfig aliyunOssConfig = (FileStorageProperties.AliyunOssConfig) properties;
        // 创建凭证提供者
        DefaultCredentialProvider provider = new DefaultCredentialProvider(aliyunOssConfig.getAccessKey(), aliyunOssConfig.getAccessSecret());
        // 配置客户端参数
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        // 显式声明使用V4签名算法
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        // 开启CNAME选项以支持自定义域名访问
        clientBuilderConfiguration.setSupportCname(true);
        // 设置通信协议为HTTPS，确保数据传输安全
        clientBuilderConfiguration.setProtocol(Protocol.HTTPS);
        // 初始化OSS客户端
        ossClient = OSSClientBuilder.create()
                .credentialsProvider(provider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(aliyunOssConfig.getRegion())
                .endpoint(aliyunOssConfig.getEndpoint())
                .build();
        log.info("----end.初始化AliyunOss连接配置完成----");
    }



    @Override
    public FileStorageDTO fileUpload(InputStream inputStream, String folderName, String fileName) {
        String objectName = folderName + "/" + UUID.randomUUID() + "_" + fileName;

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucket(), objectName, inputStream);
            ossClient.putObject(putObjectRequest);
            return FileStorageDTO.builder()
                    .objectName(objectName)
                    .absoluteUrl("") //域名访问在控制台
                    .build();
        } catch (Exception e) {
            log.error("aliyunOss文件上传失败, fileName: {},{}", fileName,e.toString());
            throw new ServiceException("文件上传异常");
        }
    }

    @Override
    public InputStream getFileInputStream(String objectName) {
        try{
            OSSObject ossObject = ossClient.getObject(properties.getBucket(), objectName);
            return ossObject.getObjectContent();
        }catch (Exception e){
            log.error("aliyunOss文件下载异常,objectName: {},{}", objectName,e.toString());
            throw new ServiceException("文件下载异常");
        }
    }

    @Override
    public void deleteFile(String objectName) {
        try{
            ossClient.deleteObject(properties.getBucket(), objectName);
        }catch (Exception e){
            log.error("aliyunOss文件删除失败,objectName: {},{}", objectName,e.toString());
            throw new ServiceException("文件删除失败");
        }
    }

    @Override
    public String getPresignedUrl(String objectName) {
        // 设置预签名URL过期时间，单位为毫秒。本示例以设置过期时间为1小时为例。
        long expireTime = 3600 * 1000L * 12;
        Date expiration = new Date(new Date().getTime() + expireTime);
        // 生成以GET方法访问的预签名URL。本示例没有额外请求头，其他人可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(properties.getBucket(), objectName, expiration);
        return url.toString();
    }

}
