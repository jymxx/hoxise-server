package cn.hoxise.common.file.service;

import cn.hoxise.common.file.api.dto.FileStorageDTO;
import cn.hoxise.common.file.pojo.enums.FileTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @Author tangxin
 * @Description: 文件存储接口
 * @Date 2024-07-16 上午9:29
 */
public interface FileStorageService {

    /**
     * 文件上传 当天的文件在一个文件夹
     * @param	file
     * @author: tangxin
     * @date: 2024/7/16 上午11:52
     */
    FileStorageDTO fileUpload(MultipartFile file);
    FileStorageDTO fileUpload(InputStream inputStream, String fileName);

    /**
     * 文件上传 带自定义路径
     * @param	file
     * @param	folderName
     * @author: tangxin
     * @date: 2024/7/16 上午11:52
     */
    FileStorageDTO fileUpload(MultipartFile file, String folderName);

    FileStorageDTO fileUpload(InputStream inputStream, String folderName, String fileName);

    /**
     * 获取文件流
     * @param	objectName
     * @author: tangxin
     * @date: 2024/7/16 上午11:59
     */
    InputStream getFileInputStream(String objectName);

    /**
     * 删除文件
     * @param	objectName
     * @author: tangxin
     * @date: 2024/7/16 上午11:53
     */
    void deleteFile(String objectName);

    /**
     * 获取预览地址
     * @param	objectName	
     * @author: tangxin
     * @date: 2024/12/5 10:49
     */
    String getPresignedUrl(String objectName);

    /**
     * 通过objectUrl获得绝对路径
     * @param	objectName
     * @author: tangxin
     * @date: 2024/7/16 上午11:45
     */
    String getAbsoluteUrl(String objectName);
}
