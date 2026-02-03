package cn.hoxise.module.self.pojo.bo;

import cn.hoxise.module.self.pojo.enums.RwTransStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 翻译子项
 *
 * @author hoxise
 * @since 2026/2/3 下午6:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RwSubTransBO {

    /**
     * 预检id
     */
    private String preCheckId;

    /**
     * 文件存储路径
     */
    private String localPath;

    /**
     * 状态
     */
    private RwTransStatus status;
}
