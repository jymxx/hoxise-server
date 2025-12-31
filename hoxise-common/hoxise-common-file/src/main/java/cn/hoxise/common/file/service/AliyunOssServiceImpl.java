package cn.hoxise.common.file.service;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.common.base.utils.date.DateUtil;
import cn.hoxise.common.file.api.dto.FileStorageDTO;
import cn.hoxise.common.file.pojo.enums.FileTypeEnum;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import io.minio.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author hoxise
 * @Description: 阿里云OSS
 * @Date 2025-12-31 下午8:18
 */
@Service
@Slf4j
@ConditionalOnProperty("fileStorage.aliyunOss.endpoint")
public class AliyunOssServiceImpl implements FileStorageService{

    @Value("${fileStorage.aliyunOss.regin}")
    private String region;

    @Value("${fileStorage.aliyunOss.endpoint}")
    private String endpoint;

    @Value("${fileStorage.aliyunOss.bucket}")
    private String bucketName;

    @Value("${fileStorage.aliyunOss.access-key}")
    private String accesskey;

    @Value("${fileStorage.aliyunOss.access-secret}")
    private String accessSecret;

    @Value("${fileStorage.serializerPrefix}")
    private String serializerPrefix;

    private OSS ossClient;

    @PostConstruct
    public void setMinioClient() {
        log.info("----初始化AliyunOss连接配置----");
        // 创建凭证提供者
        DefaultCredentialProvider provider = new DefaultCredentialProvider(accesskey, accessSecret);
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
                .region(region)
                .endpoint(endpoint)
                .build();
        log.info("----end.初始化AliyunOss连接配置完成----");
    }

    @Override
    public FileStorageDTO fileUpload(MultipartFile file) {
        String folderName = LocalDateTime.now().format(DateUtil.DATE_FORMATTER);
        return fileUpload(file,folderName);
    }

    @Override
    public FileStorageDTO fileUpload(InputStream inputStream, String fileName) {
        String folderName = LocalDateTime.now().format(DateUtil.DATE_FORMATTER);
        return fileUpload(inputStream,folderName,fileName);
    }

    @Override
    public FileStorageDTO fileUpload(MultipartFile file, String folderName) {
        try (InputStream inputStream = file.getInputStream()) {
            return fileUpload(inputStream,folderName, Objects.requireNonNull(file.getOriginalFilename()));
        } catch (IOException e) {
            log.error("aliyunOss文件处理流异常, fileName: {},{}", file.getOriginalFilename(),e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileStorageDTO fileUpload(InputStream inputStream, String folderName, String fileName) {
        String objectName = folderName + "/" + UUID.randomUUID() + "_" + fileName;

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
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
            OSSObject ossObject = ossClient.getObject(bucketName, objectName);
            return ossObject.getObjectContent();
        }catch (Exception e){
            log.error("aliyunOss文件下载异常,objectName: {},{}", objectName,e.toString());
            throw new ServiceException("文件下载异常");
        }
    }

    @Override
    public void deleteFile(String objectName) {
        try{
            ossClient.deleteObject(bucketName, objectName);
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
        URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
        return url.toString();
    }

    @Override
    public String getPresignedUrl(String objectName, FileTypeEnum fileType) {
        return "";
    }

    @Override
    public String getAbsoluteUrl(String objectName) {
        return serializerPrefix + "/" + objectName;
    }
}
