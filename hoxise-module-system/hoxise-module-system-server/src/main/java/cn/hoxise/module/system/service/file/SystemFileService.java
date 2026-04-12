package cn.hoxise.module.system.service.file;

import cn.hoxise.module.system.dal.entity.SystemFileDO;
import cn.hoxise.module.system.enums.FileBizTypeEnum;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 *
 * @author hoxise
 * @since 2026/04/12
 */
public interface SystemFileService extends IService<SystemFileDO> {

    /**
     * 上传文件
     *
     * @param file    文件
     * @param bizType 业务类型
     * @return 文件ID
     */
    Long uploadFile(MultipartFile file, FileBizTypeEnum bizType);

    /**
     * 绑定文件到业务
     *
     * @param fileId 文件ID
     * @param bizId  业务ID
     */
    void bindFile(Long fileId, Long bizId);

    /**
     * 根据文件ID获取访问URL
     *
     * @param fileId 文件ID
     * @return 访问URL
     */
    String getAccessUrl(Long fileId);

    /**
     * 根据文件ID获取ObjectName
     *
     * @param fileId 文件ID
     * @author hoxise
     * @since 2026/04/12 23:48:59
     */
    String getObjectName(Long fileId);

    /**
     * 删除文件（物理删除OSS文件 + 逻辑删除记录）
     *
     * @param fileId 文件ID
     */
    void deleteFile(Long fileId);

    /**
     * 清理过期文件（未绑定且超过指定天数的文件）
     *
     * @param expireDays 过期天数
     * @return 清理的文件数量
     */
    int cleanExpireFiles(int expireDays);
}