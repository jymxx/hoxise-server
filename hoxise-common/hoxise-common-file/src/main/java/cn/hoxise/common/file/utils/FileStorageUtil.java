package cn.hoxise.common.file.utils;

import cn.hoxise.common.file.api.FileStorageApi;
import cn.hoxise.common.file.api.dto.FileStorageDTO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;

/**
 * @Author tangxin
 * @Description: 文件存储-常用静态方法类
 * 复杂逻辑请在 {@link FileStorageApi} 实现
 * 注:如果没有配置任何文件存储实现方式 该类将无法正常使用 (fileStorageApi不会被初始化,但启动项目不会报错)
 * @Date 2024-07-16 下午12:31
 */
@Component
@ConditionalOnBean(FileStorageApi.class)
public class FileStorageUtil {

    private static FileStorageApi fileStorageApi;
    /**使用构造方法注入静态字段 */
    @Autowired
    public void setFileStorageApi(FileStorageApi fileStorageApi) {
        FileStorageUtil.fileStorageApi = fileStorageApi;
    }

    /**
     * 上传文件
     * @param	file
     * @author: tangxin
     * @date: 2024/7/16 下午1:04
     */
    public static FileStorageDTO uploadFile(MultipartFile file) {
        return fileStorageApi.uploadFile(file);
    }
    public static FileStorageDTO uploadFile(InputStream file) {
        return fileStorageApi.uploadFile(file);
    }
    public static FileStorageDTO uploadFile(InputStream file, String fileName) {
        return fileStorageApi.uploadFile(file,fileName);
    }
    public static FileStorageDTO uploadFile(InputStream file,String folderName, String fileName) {
        return fileStorageApi.uploadFile(file,folderName,fileName);
    }

    /**
     * 下载文件
     * @param	objectName
     * @param	response
     * @param	fileName 下载后的文件名称
     * @author: tangxin
     * @date: 2024/7/16 下午1:05
     */
    public static void downFile(String objectName, HttpServletResponse response, String fileName) {
        InputStream inputStream = fileStorageApi.getFileInputStream(objectName);
        response.setHeader("Content-Transfer-Encoding", "binary");
        try (ServletOutputStream stream = response.getOutputStream()) {
            FileCopyUtils.copy(inputStream, stream);
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;Filename=" + URLEncoder.encode(fileName, "UTF-8"));
            stream.flush();
        }catch (Exception e){
            throw new RuntimeException("下载文件异常"+e.getMessage());
        }
    }

    /**
     * 删除文件
     * @param	objectName
     * @author: tangxin
     * @date: 2024/7/16 下午1:04
     */
    public static void deleteFile(String objectName) {
        fileStorageApi.deleteFile(objectName);
    }


    /**
     * 通过objectName获得绝对路径
     * @param	objectName
     * @author: tangxin
     * @date: 2024/7/16 下午2:26
     */
    public static String getAbsoluteUrl(String objectName){
        return fileStorageApi.getAbsoluteUrl(objectName);
    }

    /**
     * 获取预览地址
     * @param	objectName	
     * @author: tangxin
     * @date: 2024/12/5 10:49
     */
    public static String getPresignedUrl(String objectName){
        return fileStorageApi.getPresignedUrl(objectName);
    }
}
