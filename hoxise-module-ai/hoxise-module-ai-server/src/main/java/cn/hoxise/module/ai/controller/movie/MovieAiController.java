package cn.hoxise.module.ai.controller.movie;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.common.security.satoken.uitls.SaTokenUtil;
import cn.hoxise.module.ai.pojo.enums.AiMethodEnum;
import cn.hoxise.module.ai.service.movie.AiMovieChatService;
import cn.hoxise.module.ai.service.AiRequestRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 影视 AI
 *
 * @author hoxise
 * @since 2026/01/14 14:48:36
 */
@Tag(name = "Movie AI")
@RestController
@RequestMapping("/ai/movie")
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
     * satoken貌似不兼容这个流式返回格式，拦截器会有一堆报错，暂时先手动检查
     *
     * @param token token
     * @return loginid
     * @author hoxise
     * @since 2026/01/14 14:48:51
     */
    private Long checkLogin(String token) {
        Object loginId = SaTokenUtil.getLoginIdByToken(token);
        if (loginId== null){
            throw new ServiceException("未登录");
        }
        return Long.valueOf(loginId.toString());
    }
}
