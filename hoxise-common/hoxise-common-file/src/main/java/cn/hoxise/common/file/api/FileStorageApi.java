package cn.hoxise.common.file.api;

import cn.hoxise.common.file.api.dto.FileStorageDTO;
import cn.hoxise.common.file.pojo.constants.FileRedisConstants;
import cn.hoxise.common.file.pojo.enums.FileStorageTypeEnum;
import cn.hoxise.common.file.pojo.enums.FileTypeEnum;
import cn.hoxise.common.file.service.FileStorageService;
import cn.hoxise.common.file.service.HuaWeiObsServiceImpl;
import cn.hoxise.common.file.service.MinioServiceImpl;
import cn.hoxise.common.file.utils.FileStorageUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @Author: tangxin
 * @Description: 文件存储Api实现
 * 推荐使用静态方法上传下载文件  {@link FileStorageUtil }
 * @Date: 2024/7/15 下午6:36
 */
@Slf4j
@Component
@ConditionalOnProperty("fileStorage.defaultType")
public class FileStorageApi {

    @Value("${fileStorage.defaultType}")
    private String storageType;

    @Resource
    private ApplicationContext applicationContext;

    private FileStorageService fileStorageService;

    @PostConstruct
    public void init() {
        fileStorageService = getDefaultStorage();
        log.info("---初始化默认文件存储完成");
    }

    private FileStorageService getDefaultStorage(){
        FileStorageTypeEnum typeEnum = FileStorageTypeEnum.getByName(storageType);
        return getStorageByType(typeEnum);
    }

    private FileStorageService getStorageByType(FileStorageTypeEnum typeEnum){
        return switch (typeEnum) {
            case minio -> applicationContext.getBean(MinioServiceImpl.class);
            case obs -> applicationContext.getBean(HuaWeiObsServiceImpl.class);
            default -> applicationContext.getBean(FileStorageService.class);
        };
    }

    /**
     * 文件上传
     * @param	file
     * @author: tangxin
     * @date: 2024/7/16 下午12:34
     */
    public FileStorageDTO uploadFile(MultipartFile file){
         return fileStorageService.fileUpload(file);
    }
    public FileStorageDTO uploadFile(MultipartFile file, FileStorageTypeEnum typeEnum){
        FileStorageService storageService = getStorageByType(typeEnum);
        return storageService.fileUpload(file);
    }

    public FileStorageDTO uploadFile(InputStream file){
        return fileStorageService.fileUpload(file);
    }
    public FileStorageDTO uploadFile(InputStream file,String fileName){
        return fileStorageService.fileUpload(file,fileName);
    }
    public FileStorageDTO uploadFile(InputStream file,String folderName,String fileName){
        return fileStorageService.fileUpload(file,folderName,fileName);
    }


    /**
     * 删除文件
     * @param	objectName 对象名称/存储路径
     * @author: tangxin
     * @date: 2024/7/16 下午12:45
     */
    public void deleteFile(String objectName){
        fileStorageService.deleteFile(objectName);
    }
    public void deleteFile(String objectName, FileStorageTypeEnum typeEnum){
        FileStorageService storageService = getStorageByType(typeEnum);
        storageService.deleteFile(objectName);
    }

    /**
     * 获取文件流
     * @param	objectName 对象名称/存储路径
     * @author: tangxin
     * @date: 2024/7/16 下午12:36
     */
    public InputStream getFileInputStream(String objectName){ return fileStorageService.getFileInputStream(objectName);}
    public InputStream getFileInputStream(String objectName, FileStorageTypeEnum typeEnum){
        FileStorageService storageService = getStorageByType(typeEnum);
        return storageService.getFileInputStream(objectName);
    }

    /**
     * 获取存储地址的绝对路径
     * @param	objectName 对象名称/存储路径
     * @author: tangxin
     * @date: 2024/7/16 下午2:15
     */
    public String getAbsoluteUrl(String objectName){
        return fileStorageService.getAbsoluteUrl(objectName);
    }

    /**
     * 获取预览地址
     * @param	objectName	文件对象
     * @author: tangxin
     * @date: 2024/12/5 10:28
     */
    public String getPresignedUrl(String objectName){
        return fileStorageService.getPresignedUrl(objectName);
    }

    @Cacheable(value = FileRedisConstants.PRESIGNED_URL_CACHE_KEY,key = "#objectName")
    public String getPresignedUrlCache(String objectName){
        return fileStorageService.getPresignedUrl(objectName);
    }

    @Cacheable(value = FileRedisConstants.PRESIGNED_URL_CACHE_KEY,key = "#objectName")
    public String getPresignedUrlCache(String objectName, FileTypeEnum typeEnum){
        return fileStorageService.getPresignedUrl(objectName,typeEnum);
    }
}
