package cn.hoxise.common.file.core.client;

import cn.hoxise.common.file.core.client.impl.AbstractFileClient;
import cn.hoxise.common.file.core.client.impl.AliyunOssClient;
import cn.hoxise.common.file.core.client.impl.MinioOssClient;
import cn.hoxise.common.file.core.config.FileStorageProperties;
import cn.hoxise.common.file.core.pojo.enums.FileStorageTypeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 文件存储策略工厂
 *
 * @author hoxise
 * @since 2026/01/14 06:34:13
 */
@Slf4j
public class FileStorageClientFactory {

    /**
     * 缓存已获取的服务实例
     * */
    private final ConcurrentMap<FileStorageTypeEnum, AbstractFileClient> serviceCache = new ConcurrentHashMap<>();

    @Resource
    private FileStorageProperties properties;

    /**
     * 获取默认的文件存储服务
     *
     * @return 文件存储服务
     * @author hoxise
     * @since 2026/01/14 16:04:57
     */
    public FileStorageClient getDefaultStorage(){
        return getStorageByType(properties.getDefaultType());
    }

    /**
     * 获取指定的Service
     *
     * @param typeEnum 文件存储类型枚举
     * @return 文件存储服务
     * @author hoxise
     * @since 2026/01/14 16:04:57
     */
    public FileStorageClient getStorageByType(FileStorageTypeEnum typeEnum){
        return serviceCache.computeIfAbsent(typeEnum, key -> switch (key) {
            case minio -> new MinioOssClient(properties.getMinio());
            case aliyunOss -> new AliyunOssClient(properties.getAliyunOss());
        });
    }


}
