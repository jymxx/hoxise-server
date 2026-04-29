package cn.hoxise.module.movie.dal.entity;

import cn.hoxise.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Bangumi 的infobox
 *
 * @author hoxise
 * @since 2026/01/14 14:57:12
 */
@Schema(description = "Bangumi 的信息框")
@TableName("movie_db_bangumi_infobox")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BangumiDbInfoboxDO extends BaseDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long bangumiId;

    private String infoboxKey;

    private String infoboxValue;

    @java.io.Serial
    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private static final long serialVersionUID = 1L;
}