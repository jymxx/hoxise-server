package cn.hoxise.module.movie.controller.movie.dto;

import cn.hoxise.module.movie.enums.movie.MovieTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 影视目录扫描上传DTO
 *
 * @author hoxise
 * @since 2026/3/6 上午10:41
 */
@Data
@Schema(description = "影视目录扫描上传DTO")
public class MovieScanUploadDTO {

    @Schema(description = "扫描的目录列表")
    private List<Directory> scanDirectories;

    @Schema(description = "目录名称", example = "anime")
    @NotBlank
    private MovieTypeEnum directoryName;

    @Schema(description = "是否删除缺失的目录", example = "true")
    private Boolean deleteMissing;

    @Data
    public static class Directory{

        @Schema(description = "名称", example = "Movie Name")
        @NotBlank
        private String name;

        @Schema(description = "文件大小", example = "1.5")
        private Double totalSize;

        @Schema(description = "路径", example = "D:/path/to/movie")
        private String path;

    }
}
