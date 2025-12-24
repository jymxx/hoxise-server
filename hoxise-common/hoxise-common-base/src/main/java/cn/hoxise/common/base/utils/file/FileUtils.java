package cn.hoxise.common.base.utils.file;

import java.io.File;
import java.util.Comparator;
import java.util.List;

/**
 * @Author hoxise
 * @Description: 文件工具
 * @Date 2025-08-13 上午1:17
 */
public class FileUtils {


    /**
     * @Author: hoxise
     * @Description: 递归查询文件或文件夹下最后更新时间
     * @Date: 2025/8/13 上午1:23
     */
    public static long loopLastModifiedTime(File directory) {
        if (!directory.isDirectory()){
            return directory.lastModified();
        }
        List<File> allFile = cn.hutool.core.io.FileUtil.loopFiles(directory);
        return allFile.stream().max(Comparator.comparingLong(File::lastModified)).orElse(directory).lastModified();
    }
}
