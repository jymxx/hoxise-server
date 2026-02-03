package cn.hoxise.module.ai.api;

import cn.hoxise.module.ai.enums.RpcConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 聊天
 *
 * @author hoxise
 * @since 2026/2/3 下午3:10
 */
@FeignClient(name = RpcConstants.NAME)
@Tag(name = "RPC 聊天接口")
public interface ChatApi {

    String PREFIX = RpcConstants.API_PREFIX + "/chat";

    @Operation(summary = "聊天",description = "其它模块可能需要最基础的AI调用")
    @GetMapping(PREFIX + "/chat")
    @Parameters({
            @Parameter(name = "systemText", description = "系统提示语",example = "你是一个AI助手", required = true),
            @Parameter(name = "userText", description = "用户输入",example = "你好", required = true)
    })
    String chat(String systemText, String userText);

}
