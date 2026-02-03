package cn.hoxise.module.self.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hoxise
 * @since 2026/2/3 下午7:25
 */
@Data
public class RwTransStartDTO {

    /**
     * 预检id
     */
    @NotNull(message = "预检id不能为空")
    private String preCheckId;

    /**
     * 作者
     */
    private String author;

    /**
     * 包ID
     */
    private String packageId;

    /**
     * 用户提示词
     */
    private String userPrompt;

}
