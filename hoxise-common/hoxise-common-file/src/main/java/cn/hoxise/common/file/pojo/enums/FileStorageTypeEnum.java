package cn.hoxise.common.file.pojo.enums;

/**
 * @Author tangxin
 * @Description: 存储类型枚举
 * @Date 2024-07-15 下午6:27
 */
public enum FileStorageTypeEnum {

    unknown(-1,"未知","unknown"),
    minio(0,"minio","minio对象存储"),
    obs(1,"huaweiObs","华为云对象存储");

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
        return unknown;
    }

    public static FileStorageTypeEnum getByName(String name) {
        FileStorageTypeEnum[] values = FileStorageTypeEnum.values();
        for (FileStorageTypeEnum value : values) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return unknown;
    }
}
