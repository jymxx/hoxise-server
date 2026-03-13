package cn.hoxise.common.file.core.client.impl;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.common.file.core.config.FileStorageProperties;
import cn.hoxise.common.file.core.pojo.FileStorageDTO;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.UUID;

/**
 * Minio存储实现
 *
 * @author hoxise
 * @since 2026/01/14 06:53:37
 */
@Slf4j
public class MinioOssClient extends AbstractFileClient {

    private MinioClient minioClient;

    public MinioOssClient(FileStorageProperties.MinioConfig clientProperties) {
        super(clientProperties);
    }

    @Override
    protected void doInit() {
        log.info("----初始化minio连接配置----");
        FileStorageProperties.MinioConfig minioConfig = (FileStorageProperties.MinioConfig) properties;
        minioClient = io.minio.MinioClient.builder()
                .credentials(minioConfig.getAccessKey(), minioConfig.getAccessSecret())
                .endpoint(minioConfig.getEndpoint())
                .build();
        log.info("----end.初始化minio连接配置完成----");
    }



    @Override
    public FileStorageDTO uploadFile(InputStream inputStream, String folderName, String fileName) {
        String objectName = folderName + "/"
                + UUID.randomUUID() + "_" + fileName;
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(properties.getBucket())
                    .object(objectName)
                    .stream(inputStream, -1, 10485760) // -1 表示未知长度
                    .build());

            return FileStorageDTO.builder()
                    .objectName(objectName)
                    .absoluteUrl(getAbsoluteUrl(objectName))
                    .build();
        } catch (Exception e) {
            log.error("minio文件上传失败, fileName: {},{}", fileName,e.toString());
            throw new ServiceException("文件上传异常");
        }

    }

    @Override
    public InputStream getFileInputStream(String objectName){
        try{
            return minioClient.getObject(GetObjectArgs.builder().bucket(properties.getBucket()).object(objectName).build());
        }catch (Exception e){
            log.error("minio文件下载异常,objectName: {},{}", objectName,e.toString());
            throw new ServiceException("文件下载异常");
        }

    }

    @Override
    public void deleteFile(String objectName) {
        try{
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(properties.getBucket()).object(objectName).build());
        }catch (Exception e){
            log.error("minio文件删除失败,objectName: {},{}", objectName,e.toString());
            throw new ServiceException("文件删除失败");
        }
    }


    @Override
    public String getPresignedUrl(String objectName) {
        try{
            minioClient.statObject(StatObjectArgs.builder().bucket(properties.getBucket()).object(objectName).build());
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(properties.getBucket()).object(objectName).build());
        }catch (Exception e){
            log.error("minio获取文件预览地址异常,objectName:{},{}", objectName,e.toString());
            throw new ServiceException("获取文件预览地址异常");
        }
    }


}
