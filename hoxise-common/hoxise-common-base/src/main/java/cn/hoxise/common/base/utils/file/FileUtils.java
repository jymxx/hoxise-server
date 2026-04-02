package cn.hoxise.common.base.utils.file;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

/**
 * FileUtils 文件工具
 *
 * @author hoxise
 * @since 2026/01/14 06:49:53
 */
public class FileUtils {


    /**
     * 递归查询文件或文件夹下最后更新时间
     *
     * @param directory 扫描目录
     * @return long
     * @author hoxise
     * @since 2026/01/14 06:50:00
     */
    public static long loopLastModifiedTime(File directory) {
        if (!directory.isDirectory()){
            return directory.lastModified();
        }
        List<File> allFile = cn.hutool.core.io.FileUtil.loopFiles(directory);
        return allFile.stream().max(Comparator.comparingLong(File::lastModified)).orElse(directory).lastModified();
    }


    /**
     * downFile 下载文件到前端
     *
     * @param inputStream 文件流
     * @param response   HttpServletResponse
     * @param fileName   下载后的文件名称
     * @author hoxise
     * @since 2026/01/14 06:57:54
     */
    public static void downFile(InputStream inputStream, HttpServletResponse response, String fileName) {
        response.setHeader("Content-Transfer-Encoding", "binary");
        try (ServletOutputStream stream = response.getOutputStream()) {
            FileCopyUtils.copy(inputStream, stream);
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;Filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            stream.flush();
        }catch (Exception e){
            throw new RuntimeException("下载文件异常"+e.getMessage());
        }
    }

    /**
     * 获取文件扩展名（包含点）
     */
    public static String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

}
