package cn.hoxise.module.self.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author hoxise
 * @since 2026/2/3 下午4:08
 */
@Data
public class RwTransPreCheckDTO {

    /**
     * 前端获取到的文件列表
     */
    @NotEmpty(message = "请上传文件")
    private List<String> paths;

}
