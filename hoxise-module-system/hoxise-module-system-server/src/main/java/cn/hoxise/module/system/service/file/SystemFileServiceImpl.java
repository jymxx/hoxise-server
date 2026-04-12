package cn.hoxise.module.system.service.file;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.common.file.core.client.FileStorageClientFactory;
import cn.hoxise.common.file.core.pojo.FileStorageDTO;
import cn.hoxise.module.system.dal.entity.SystemFileDO;
import cn.hoxise.module.system.dal.mapper.SystemFileMapper;
import cn.hoxise.module.system.enums.FileBindStatusEnum;
import cn.hoxise.module.system.enums.FileBizTypeEnum;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文件服务实现
 *
 * @author hoxise
 * @since 2026/04/12
 */
@Slf4j
@Service
public class SystemFileServiceImpl extends ServiceImpl<SystemFileMapper, SystemFileDO> implements SystemFileService {

    @Resource
    private FileStorageClientFactory fileStorageClientFactory;

    @Override
    public Long uploadFile(MultipartFile file, FileBizTypeEnum bizType) {
        // 校验文件
        validateFile(file, bizType);

        // 获取文件拓展名
        String extension = FileUtil.getSuffix(file.getOriginalFilename());

        // 上传到OSS
        FileStorageDTO fileStorageDTO = fileStorageClientFactory.getDefaultStorage().uploadFile(file, bizType.getOssDir());

        // 保存记录
        SystemFileDO fileDO = SystemFileDO.builder()
                .objectName(fileStorageDTO.getObjectName())
                .originalName(file.getOriginalFilename())
                .fileSize(file.getSize())
                .fileType(file.getContentType())
                .extension(extension)
                .bizType(bizType.getType())
                .bindStatus(FileBindStatusEnum.UNBIND.getStatus())
                .build();
        save(fileDO);

        return fileDO.getId();
    }

    @Override
    public void bindFile(Long fileId, Long bizId) {
        update(Wrappers.lambdaUpdate(SystemFileDO.class)
                .eq(SystemFileDO::getId, fileId)
                .set(SystemFileDO::getBizId, bizId)
                .set(SystemFileDO::getBindStatus, FileBindStatusEnum.BIND.getStatus()));
    }

    @Override
    public String getAccessUrl(Long fileId) {
        SystemFileDO fileDO = getById(fileId);
        if (fileDO == null) {
            return null;
        }
        return fileStorageClientFactory.getDefaultStorage().getPresignedUrl(fileDO.getObjectName());
    }

    @Override
    public String getObjectName(Long fileId) {
        SystemFileDO fileDO = getById(fileId);
        if (fileDO == null) {
            return null;
        }
        return fileDO.getObjectName();
    }

    @Override
    public void deleteFile(Long fileId) {
        SystemFileDO fileDO = getById(fileId);
        Assert.notBlank(fileDO.getObjectName(), "文件不存在");
        // 删除OSS文件
        try {
            fileStorageClientFactory.getDefaultStorage().deleteFile(fileDO.getObjectName());
        } catch (Exception e) {
            log.warn("OSS文件删除失败: {}", fileDO.getObjectName(), e);
        }
        // 逻辑删除记录
        removeById(fileId);
    }

    /**
     * 校验文件
     */
    private void validateFile(MultipartFile file, FileBizTypeEnum bizType) {
        if (file == null || file.isEmpty()) {
            throw new ServiceException("文件不能为空");
        }

        // 根据业务类型校验
        if (bizType == FileBizTypeEnum.AVATAR) {
            // 头像校验：只允许图片，最大10MB
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new ServiceException("头像只能上传图片文件");
            }
            long maxSize = 10 * 1024 * 1024;
            if (file.getSize() > maxSize) {
                throw new ServiceException("头像文件大小不能超过10MB");
            }
        }
    }

    @Override
    public int cleanExpireFiles(int expireDays) {
        // 查询未绑定且超过指定天数的文件
        LocalDateTime expireTime = LocalDateTime.now().minusDays(expireDays);
        List<SystemFileDO> orphanFiles = list(Wrappers.lambdaQuery(SystemFileDO.class)
                .eq(SystemFileDO::getBindStatus, FileBindStatusEnum.UNBIND.getStatus())
                .lt(SystemFileDO::getCreateTime, expireTime));

        if (orphanFiles.isEmpty()) {
            log.info("没有需要清理的过期文件");
            return 0;
        }

        int count = 0;
        for (SystemFileDO fileDO : orphanFiles) {
            try {
                // 删除OSS文件
                fileStorageClientFactory.getDefaultStorage().deleteFile(fileDO.getObjectName());
                // 删除数据库记录（物理删除）
                baseMapper.deleteById(fileDO.getId());
                count++;
            } catch (Exception e) {
                log.warn("清理过期文件失败: {}", fileDO.getObjectName(), e);
            }
        }
        return count;
    }
}