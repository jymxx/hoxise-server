package cn.hoxise.self.biz.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author hoxise
 * @Description: AI接口枚举
 * @Date 2025-12-25 下午3:11
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
