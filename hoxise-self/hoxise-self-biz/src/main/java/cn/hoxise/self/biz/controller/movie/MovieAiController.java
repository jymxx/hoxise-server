package cn.hoxise.self.biz.controller.movie;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.self.biz.pojo.enums.AiMethodEnum;
import cn.hoxise.self.biz.service.ai.AiMovieChatService;
import cn.hoxise.self.biz.service.ai.AiMovieChatServiceImpl;
import cn.hoxise.self.biz.service.ai.AiRequestRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Author hoxise
 * @Description: movie Ai
 * @Date 2025-12-25 上午5:49
 */
@Tag(name = "Movie AI")
@RestController
@RequestMapping("/movie/ai")
public class MovieAiController {

    @Resource
    private AiMovieChatService movieChatService;

    @Resource
    private AiRequestRecordService aiRequestRecordService;

    @Operation(summary = "ai总结")
    @RequestMapping(value = "/aiSummary",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> aiSummary(Long catalogid, String token){
        Long userid = checkLogin(token);
        aiRequestRecordService.record(AiMethodEnum.AiSummary,userid);//记录日志

        return movieChatService.aiSummary(catalogid).concatWith(Flux.just("[DONE]"));
    }

    @Operation(summary = "ai推荐")
    @RequestMapping(value = "/aiRecommend",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> aiRecommend(String userText,String chatId, String token,String mode){
        Long userid = checkLogin(token);
        aiRequestRecordService.record(AiMethodEnum.AiRecommend,userid);//记录日志
        aiRequestRecordService.aiRateLimit(userid);//限制请求次数
        return movieChatService.aiRecommend(userText,chatId,userid,mode).concatWith(Flux.just("[DONE]"));
    }


    /**
     * @Author: hoxise
     * @Description: satoken貌似不兼容这个流式返回格式，拦截器会有一堆报错，只能手动检查
     * @Date: 2025/12/25 下午6:31
     */
    private Long checkLogin(String token) {
        Object loginId = StpUtil.getLoginIdByToken(token);
        if (loginId== null){
            throw new ServiceException("未登录");
        }
        return Long.valueOf(loginId.toString());
    }
}
