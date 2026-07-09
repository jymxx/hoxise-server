package cn.hoxise.module.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件业务类型枚举
 *
 * @author hoxise
 * @since 2026/04/12
 */
@Getter
@AllArgsConstructor
public enum FileOssDirEnum {

    AVATAR("avatar", "用户头像", "user/avatar"),
    MOVIE_RESOURCE("movie_resource", "动漫资源", "movie/resource"),
    MOVIE_BANGUMI_IMG("movie_bangumi_img", "bangumi图片", "movie/bangumi/img"),
    ;

    private final String type;
    private final String description;
    private final String ossDir;

}