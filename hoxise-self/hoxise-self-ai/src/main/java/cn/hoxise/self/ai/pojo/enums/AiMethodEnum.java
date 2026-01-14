package cn.hoxise.self.ai.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Ai接口枚举
 *
 * @author hoxise
 * @since 2026/01/14 14:50:16
 */
@Getter
@AllArgsConstructor
public enum AiMethodEnum {

    AiSummary("aiSummary","ai生成总结"),
    AiRecommend("aiRecommend","ai推荐");

    @EnumValue
    private final String method;
    private final String desc;
}
