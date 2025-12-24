package cn.hoxise.common.file.service;

import cn.hoxise.common.file.api.dto.FileStorageDTO;
import cn.hoxise.common.file.pojo.enums.FileTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @Author tangxin
 * @Description: 华为Obs对象存储
 * @Date 2024-07-16 上午10:15
 */
public class HuaWeiObsServiceImpl implements FileStorageService{

    @Override
    public FileStorageDTO fileUpload(MultipartFile file) {
        return null;
    }

    @Override
    public FileStorageDTO fileUpload(InputStream inputStream) {
        return null;
    }

    @Override
    public FileStorageDTO fileUpload(InputStream inputStream, String fileName) {
        return null;
    }

    @Override
    public FileStorageDTO fileUpload(MultipartFile file, String folderName) {
        return null;
    }

    @Override
    public FileStorageDTO fileUpload(InputStream inputStream, String folderName, String fileName) {
        return null;
    }

    @Override
    public InputStream getFileInputStream(String objectName) {
        return null;
    }

    @Override
    public void deleteFile(String objectName) {

    }

    @Override
    public String getPresignedUrl(String objectName) {
        return "";
    }

    @Override
    public String getPresignedUrl(String objectName, FileTypeEnum fileType) {
        return "";
    }

    @Override
    public String getAbsoluteUrl(String objectName) {
        return "";
    }
}
