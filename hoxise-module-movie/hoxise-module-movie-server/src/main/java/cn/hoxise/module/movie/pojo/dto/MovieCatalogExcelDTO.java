package cn.hoxise.module.movie.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 影视目录上传excel
 *
 * @author hoxise
 * @since 2026/2/28 上午8:21
 */
@Data
public class MovieCatalogExcelDTO {

    @ExcelProperty(value = "名称")
    private String name;

    @ExcelProperty(value = "路径")
    private String path;

    @ExcelProperty(value = "文件大小 (GB)")
    private Double size;

    @ExcelProperty(value = "备注", index = 3)
    private String remark;

}
