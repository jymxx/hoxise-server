package cn.hoxise.common.file.core.client.impl;

import cn.hoxise.common.base.utils.date.DateUtil;
import cn.hoxise.common.base.utils.file.FileUtils;
import cn.hoxise.common.file.core.client.FileStorageClient;
import cn.hoxise.common.file.core.config.FileStorageProperties;
import cn.hoxise.common.file.core.pojo.FileMetadataDTO;
import cn.hoxise.common.file.core.pojo.FileStorageDTO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * 抽象类 提供模版方法简化实现类代码
 *
 * @author hoxise
 * @since 2026/2/3 上午10:47
 */
@Slf4j
public abstract class AbstractFileClient implements FileStorageClient {

    /**
     * 配置属性
     */
    protected FileStorageProperties.ClientProperties properties;

    // 默认目录前缀
    protected static final String DEFAULT_FOLDER_NAME = "default";

    public AbstractFileClient(FileStorageProperties.ClientProperties clientProperties) {
        if (BeanUtil.hasNullField(clientProperties)){
            log.error("-----！！！请检查文件存储配置.");
            throw new RuntimeException("文件存储功能异常");
        }
        this.properties = clientProperties;
        doInit();//初始化
    }

    protected abstract void doInit();

    @Override
    public FileStorageDTO uploadFile(MultipartFile file) {
        String folderName = DEFAULT_FOLDER_NAME + "/" + LocalDateTime.now().format(DateUtil.DATE_FORMATTER);
        return uploadFile(file,folderName);
    }

    @Override
    public FileStorageDTO uploadFile(InputStream inputStream, String fileName) {
        String folderName = DEFAULT_FOLDER_NAME + "/" +LocalDateTime.now().format(DateUtil.DATE_FORMATTER);
        return uploadFile(inputStream,folderName,fileName);
    }

    @Override
    public FileStorageDTO uploadFile(MultipartFile file, String folderName) {
        String originalFilename = file.getOriginalFilename();
        if (Objects.isNull(originalFilename)){
            originalFilename = "file_" + UUID.randomUUID();
        }
        try (InputStream inputStream = file.getInputStream()) {
            String newFileName = UUID.randomUUID() + "_" + originalFilename;
            return uploadFile(inputStream, folderName, newFileName);
        } catch (IOException e) {
            log.error("Oss文件处理流异常, fileName: {},{}", originalFilename,e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAbsoluteUrl(String objectName){
        return properties.getSerializerPrefix() + "/" + objectName;
    }


    @Override
    public String generatePresignedUrl(String objectName, String contentType) {
        throw new UnsupportedOperationException("当前存储实现不支持生成预签名URL操作");
    }

    @Override
    public FileMetadataDTO getObjectMetadata(String objectName) {
        throw new UnsupportedOperationException("当前存储实现不支持获取对象元数据操作");
    }

    @Override
    public boolean doesObjectExist(String objectName) {
        throw new UnsupportedOperationException("当前存储实现不支持判断文件是否存在");
    }
}
