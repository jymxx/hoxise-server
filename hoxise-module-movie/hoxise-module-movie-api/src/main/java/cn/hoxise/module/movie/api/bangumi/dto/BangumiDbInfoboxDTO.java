package cn.hoxise.module.movie.api.bangumi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Bangumi 的infobox
 *
 * @author hoxise
 * @since 2026/01/14 14:57:12
 */
@Schema(description = "Bangumi 的信息框")
@Data
public class BangumiDbInfoboxDTO {

    private Long id;

    /**
     * 目录id
     */
    private Long catalogid;

    /**
     * bangumi id
     */
    private Long bangumiId;

    private String infoboxKey;

    private String infoboxValue;
}