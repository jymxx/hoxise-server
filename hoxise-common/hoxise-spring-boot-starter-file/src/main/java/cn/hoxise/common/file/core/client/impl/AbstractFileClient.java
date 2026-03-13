package cn.hoxise.common.file.core.client.impl;

import cn.hoxise.common.base.utils.date.DateUtil;
import cn.hoxise.common.file.core.client.FileStorageClient;
import cn.hoxise.common.file.core.config.FileStorageProperties;
import cn.hoxise.common.file.core.pojo.FileStorageDTO;
import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Objects;

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

    protected String serializerPrefix;

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
        String folderName = LocalDateTime.now().format(DateUtil.DATE_FORMATTER);
        return uploadFile(file,folderName);
    }

    @Override
    public FileStorageDTO uploadFile(InputStream inputStream, String fileName) {
        String folderName = LocalDateTime.now().format(DateUtil.DATE_FORMATTER);
        return uploadFile(inputStream,folderName,fileName);
    }

    @Override
    public FileStorageDTO uploadFile(MultipartFile file, String folderName) {
        try (InputStream inputStream = file.getInputStream()) {
            return uploadFile(inputStream,folderName, Objects.requireNonNull(file.getOriginalFilename()));
        } catch (IOException e) {
            log.error("Oss文件处理流异常, fileName: {},{}", file.getOriginalFilename(),e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAbsoluteUrl(String objectName){
        return serializerPrefix + "/" + objectName;
    }

}
