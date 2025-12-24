package cn.hoxise.common.file.pojo.enums;

import lombok.AllArgsConstructor;

/**
 * @Author hoxise
 * @Description: 文件类型枚举
 * @Date 2025-12-22 下午6:23
 */
@AllArgsConstructor
public enum FileTypeEnum {

    defaultType("defaultType"),
    img("img");

    private final String type;
}
