package cn.hoxise.common.file.core.pojo.enums;

/**
 * 文件存储类型枚举
 *
 * @author hoxise
 * @since 2026/01/14 06:47:15
 */
public enum FileStorageTypeEnum {

    minio(1,"minio","minio对象存储"),
    aliyunOss(2,"aliyunOss","阿里云对象存储");

    private final Integer code;
    private final String name;
    private final String description;

    FileStorageTypeEnum(Integer code, String name,String description){
        this.code=code;
        this.name=name;
        this.description =description;
    }

    public static FileStorageTypeEnum getByCode(Integer code) {
        FileStorageTypeEnum[] values = FileStorageTypeEnum.values();
        for (FileStorageTypeEnum value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static FileStorageTypeEnum getByName(String name) {
        FileStorageTypeEnum[] values = FileStorageTypeEnum.values();
        for (FileStorageTypeEnum value : values) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }
}
