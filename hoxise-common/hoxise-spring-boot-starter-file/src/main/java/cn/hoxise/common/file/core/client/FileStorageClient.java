package cn.hoxise.common.file.core.client;

import cn.hoxise.common.file.core.pojo.FileStorageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 文件存储服务
 *
 * @author hoxise
 * @since 2026/01/14 06:54:14
 */
public interface FileStorageClient {

    /**
     * 文件上传 当天的文件在一个文件夹
     *
     * @param file 文件对象
     * @return 文件存储结果DTO
     * @author hoxise
     * @since 2026/01/14 06:54:43
     */
    FileStorageDTO uploadFile(MultipartFile file);
    
    /**
     * 文件上传 使用输入流
     *
     * @param inputStream 输入流
     * @param fileName 文件名
     * @return 文件存储结果DTO
     * @author hoxise
     * @since 2026/01/14 06:56:45
     */
    FileStorageDTO uploadFile(InputStream inputStream, String fileName);

    /**
     * 文件上传 带自定义路径
     *
     * @param file 文件对象
     * @param folderName 文件夹名称
     * @return 文件存储结果DTO
     * @author hoxise
     * @since 2026/01/14 06:56:45
     */
    FileStorageDTO uploadFile(MultipartFile file, String folderName);

    /**
     * 文件上传 使用输入流和自定义路径
     *
     * @param inputStream 输入流
     * @param folderName 文件夹名称
     * @param fileName 文件名
     * @return 文件存储结果DTO
     * @author hoxise
     * @since 2026/01/14 06:56:45
     */
    FileStorageDTO uploadFile(InputStream inputStream, String folderName, String fileName);

    /**
     * 获取文件流
     *
     * @param objectName 对象名称
     * @return 文件输入流
     * @author hoxise
     * @since 2026/01/14 06:56:45
     */
    InputStream getFileInputStream(String objectName);

    /**
     * 删除文件
     *
     * @param objectName 对象名称
     * @author hoxise
     * @since 2026/01/14 06:56:45
     */
    void deleteFile(String objectName);

    /**
     * 获取预览地址
     *
     * @param objectName 对象名称
     * @return 预签名URL
     * @author hoxise
     * @since 2026/01/14 06:56:45
     */
    String getPresignedUrl(String objectName);

    /**
     * 获取可直接访问的绝对路径地址
     * OSS需要配置公开桶
     *
     * @param objectName 对象名称
     * @return 绝对路径
     * @author hoxise
     * @since 2026/03/12 13:34:40
     */
    String getAbsoluteUrl(String objectName);
}