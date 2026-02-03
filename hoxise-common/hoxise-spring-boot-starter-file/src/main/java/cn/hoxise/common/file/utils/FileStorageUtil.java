package cn.hoxise.common.file.utils;

import cn.hoxise.common.file.core.client.FileStorageClientFactory;
import cn.hoxise.common.file.core.config.FileStorageProperties;
import cn.hoxise.common.file.core.pojo.FileStorageDTO;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 文件存储-默认存储方式的常用方法类
 *
 * @author hoxise
 * @since 2026/01/14 06:58:23
 */
public class FileStorageUtil {

    @Resource private FileStorageClientFactory fileStorageClientFactory;

    private static String serializerPrefix;
    @Autowired
    public void setSerializerPrefix(FileStorageProperties properties) {
        FileStorageUtil.serializerPrefix = properties.getSerializerPrefix();
    }

    /**
     * uploadFile
     *
     * @param file 文件
     * @return 文件存储结果DTO
     * @author hoxise
     * @since 2026/01/14 06:58:12
     */
    public FileStorageDTO uploadFile(MultipartFile file) {
        return fileStorageClientFactory.getDefaultStorage().fileUpload(file);
    }
    public FileStorageDTO uploadFile(InputStream file, String fileName) {
        return fileStorageClientFactory.getDefaultStorage().fileUpload(file,fileName);
    }
    public FileStorageDTO uploadFile(InputStream file,String folderName, String fileName) {
        return fileStorageClientFactory.getDefaultStorage().fileUpload(file,folderName,fileName);
    }

    /**
     * 删除文件
     *
     * @param objectName 存储路径
     * @author hoxise
     * @since 2026/01/14 15:25:46
     */
    public void deleteFile(String objectName) {
        fileStorageClientFactory.getDefaultStorage().deleteFile(objectName);
    }

    /**
     * 获取预览地址
     *
     * @param objectName 存储路径
     * @return 预签名URL
     * @author hoxise
     * @since 2026/01/14 15:26:25
     */
    public String getPresignedUrl(String objectName){
        return fileStorageClientFactory.getDefaultStorage().getPresignedUrl(objectName);
    }


    /**
     * 通过objectName获得绝对路径
     *
     * @param objectName 存储路径
     * @return 绝对路径
     * @author hoxise
     * @since 2026/01/14 15:26:03
     */
    public static String getAbsoluteUrl(String objectName){
        return serializerPrefix + "/" + objectName;
    }
}
