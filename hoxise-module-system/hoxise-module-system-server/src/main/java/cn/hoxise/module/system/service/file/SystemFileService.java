package cn.hoxise.module.system.service.file;

import cn.hoxise.module.system.controller.file.dto.PresignedUploadReqDTO;
import cn.hoxise.module.system.controller.file.vo.PresignedUploadVO;
import cn.hoxise.module.system.dal.entity.SystemFileDO;
import cn.hoxise.module.system.enums.FileOssDirEnum;
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
     * @param bizType 业务类型（用于校验和OSS路径）
     * @return 文件ID
     * @author hoxise
     * @since 2026/07/09 11:29:46
     */
    String uploadFile(MultipartFile file, FileOssDirEnum bizType);

    /**
     * 绑定文件
     *
     * @param fileId 文件ID
     * @author hoxise
     * @since 2026/07/09 11:29:43
     */
    void bindFile(String fileId);

    /**
     * 根据文件ID获取访问URL
     *
     * @param fileId 文件ID
     * @return 访问URL
     * @author hoxise
     * @since 2026/07/09 11:30:08
     */
    String getAccessUrl(String fileId);

    /**
     * 根据文件ID获取下载URL
     * 与getAccessUrl不同的是 会强制浏览器下载而不是预览例如图片文件
     *
     * @param fileId 文件ID
     * @return 下载URL
     * @author hoxise
     * @since 2026/07/09 11:30:08
     */
    String getDownloadUrl(String fileId);

    /**
     * 删除文件（物理删除OSS文件 + 逻辑删除记录）
     *
     * @param fileId 文件ID
     * @author hoxise
     * @since 2026/07/09 11:30:17
     */
    void deleteFile(String fileId);

    /**
     * 清理过期文件（未绑定且超过指定天数的文件）
     *
     * @param expireDays 过期天数
     * @return 清理的文件数量
     * @author hoxise
     * @since 2026/07/09 11:30:27
     */
    int cleanExpireFiles(int expireDays);

    /**
     * 生成预签名上传 URL（前端直传 OSS，保存记录并返回文件 ID）
     *
     * @param reqDTO 请求体（文件名 + 业务类型）
     * @return 预签名上传响应
     * @author hoxise
     * @since 2026/07/14
     */
    PresignedUploadVO generatePresignedUrl(PresignedUploadReqDTO reqDTO);
}