package cn.hoxise.module.self.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 接收文件
 *
 * @author hoxise
 * @since 2026/2/3 下午4:38
 */
@Data
public class RwTransAcceptFileDTO {

    /**
     * 预检id
     */
    @NotNull(message = "预检id不能为空")
    private String preCheckId;

    /**
     * 相对路径
     * 相对于Languages文件夹的路径
     */
    @NotBlank
    private String path;

    @NotNull
    private MultipartFile file;
}
