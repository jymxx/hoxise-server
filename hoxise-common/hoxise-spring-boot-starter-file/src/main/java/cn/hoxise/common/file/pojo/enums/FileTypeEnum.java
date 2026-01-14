package cn.hoxise.common.file.pojo.enums;

import lombok.AllArgsConstructor;

/**
 * 文件类型枚举
 *
 * @author hoxise
 * @since 2026/01/14 06:53:08
 */
@AllArgsConstructor
public enum FileTypeEnum {

    defaultType("defaultType"),
    img("img");

    private final String type;
}
