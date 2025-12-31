package cn.hoxise.common.file.service;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.common.base.utils.date.DateUtil;
import cn.hoxise.common.file.api.dto.FileStorageDTO;
import cn.hoxise.common.file.pojo.enums.FileTypeEnum;
import cn.hutool.core.util.StrUtil;
import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author tangxin
 * @Description: minio实现类
 * @Date 2024-07-16 上午9:38
 */
@Service
@Slf4j
@ConditionalOnProperty("fileStorage.minio.endpoint")
public class MinioServiceImpl implements FileStorageService {

    @Value("${fileStorage.minio.endpoint}")
    private String endpoint;

    @Value("${fileStorage.minio.bucket}")
    private String bucketName;

    @Value("${fileStorage.minio.access-key}")
    private String accesskey;

    @Value("${fileStorage.minio.secret-key}")
    private String secretKey;

    private MinioClient minioClient;

    @PostConstruct
    public void setMinioClient() {
        log.info("----初始化minio连接配置----");
        minioClient = MinioClient.builder()
                .credentials(accesskey,secretKey)
                .endpoint(endpoint)
                .build();
        log.info("----end.初始化minio连接配置完成----");
    }

    @Override
    public FileStorageDTO fileUpload(MultipartFile file) {
        String folderName = LocalDateTime.now().format(DateUtil.DATE_FORMATTER);
        return fileUpload(file,folderName);
    }

    @Override
    public FileStorageDTO fileUpload(InputStream inputStream) {
        String folderName = LocalDateTime.now().format(DateUtil.DATE_FORMATTER);
        return fileUpload(inputStream,folderName,UUID.randomUUID().toString());
    }

    @Override
    public FileStorageDTO fileUpload(InputStream inputStream,String fileName) {
        String folderName = LocalDateTime.now().format(DateUtil.DATE_FORMATTER);
        return fileUpload(inputStream,folderName,fileName);
    }

    @Override
    public FileStorageDTO fileUpload(MultipartFile file, String folderName) {
        try (InputStream inputStream = file.getInputStream()) {
           return fileUpload(inputStream,folderName,Objects.requireNonNull(file.getOriginalFilename()));
        } catch (IOException e) {
            log.error("minio文件处理异常, fileName: {},{}", file.getOriginalFilename(),e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileStorageDTO fileUpload(InputStream inputStream, String folderName, String fileName) {
        String objectName = folderName + "/"
                + UUID.randomUUID() + "_" + fileName;
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, -1, 10485760) // -1 表示未知长度
                    .build());

            return FileStorageDTO.builder()
                    .objectName(objectName)
                    .absoluteUrl(getAbsoluteUrl(fileName))
                    .build();
        } catch (Exception e) {
            log.error("minio文件上传失败, fileName: {},{}", fileName,e.toString());
            throw new ServiceException("文件上传异常");
        }

    }

    @Override
    public InputStream getFileInputStream(String objectName){
        try{
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        }catch (Exception e){
            log.error("minio文件下载异常,objectName: {},{}", objectName,e.toString());
            throw new ServiceException("文件下载异常");
        }

    }

    @Override
    public void deleteFile(String objectName) {
        try{
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        }catch (Exception e){
            log.error("minio文件删除失败,objectName: {},{}", objectName,e.toString());
            throw new ServiceException("文件删除失败");
        }
    }


    @Override
    public String getPresignedUrl(String objectName) {
        try{
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(objectName).build());
        }catch (Exception e){
            log.error("minio获取文件预览地址异常,objectName:{},{}", objectName,e.toString());
            throw new ServiceException("获取文件预览地址异常");
        }
    }

    @Override
    public String getPresignedUrl(String objectName, FileTypeEnum fileType) {
        if (StrUtil.isBlank(objectName)){
            return "";
        }
        try{
            Map<String, String> reqParams = new HashMap<>();
            //图片类型
            if (fileType == FileTypeEnum.img){
                reqParams.put("response-content-disposition", "inline");
            }
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(objectName).extraQueryParams(reqParams).build());
        }catch (Exception e){
            log.error("minio获取文件预览地址异常,objectName{},{}", objectName,e.toString());
            throw new ServiceException("获取文件预览地址异常");
        }
    }

    @Override
    public String getAbsoluteUrl(String objectName){
        return endpoint+"/"+bucketName+"/"+objectName;
    }

}
