package cn.hoxise.module.self.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hoxise
 * @since 2026/2/3 下午6:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RwTransBO {

    /**
     * 身份标识
     */
    private String identifier;

    /**
     * 预检id
     */
    private String preCheckId;

    /**
     * 文件路径
     */
    private List<String> paths;

    /**
     * mod名称
     */
    private String modName;
    /**
     * 作者
     */
    private String author;

    /**
     * 包ID
     */
    private String packageId;

}
