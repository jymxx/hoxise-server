package cn.hoxise.module.movie.dal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Bangumi 的infobox
 *
 * @author hoxise
 * @since 2026/01/14 14:57:12
 */
@Schema(description = "Bangumi 的信息框")
@TableName("movie_db_bangumi_infobox")
@Data
@Builder
public class BangumiDbInfoboxDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long catalogid;

    private Long bangumiId;

    private String infoboxKey;

    private String infoboxValue;
}