package cn.hoxise.common.file.core.client;

import cn.hoxise.common.file.core.client.impl.AliyunOssClient;
import cn.hoxise.common.file.core.client.impl.MinioOssClient;
import cn.hoxise.common.file.pojo.enums.FileStorageTypeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件存储策略工厂
 *
 * @author hoxise
 * @since 2026/01/14 06:34:13
 */
@Slf4j
public class FileStorageFactory {

    @Value("${fileStorage.defaultType}")
    private String defaultStorageType;

    @Resource
    private ApplicationContext applicationContext;

    // 缓存已获取的服务实例，避免重复查询
    private final Map<FileStorageTypeEnum, FileStorageClient> serviceCache = new ConcurrentHashMap<>(8);

    /**
     * 获取默认的文件存储服务
     *
     * @return 文件存储服务
     * @author hoxise
     * @since 2026/01/14 16:04:57
     */
    public FileStorageClient getDefaultStorage(){
        FileStorageTypeEnum typeEnum = FileStorageTypeEnum.getByName(defaultStorageType);
        return getStorageByType(typeEnum);
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
            case minio -> applicationContext.getBean(MinioOssClient.class);
            case aliyunOss -> applicationContext.getBean(AliyunOssClient.class);
            default -> applicationContext.getBean(FileStorageClient.class);
        });
    }

}
