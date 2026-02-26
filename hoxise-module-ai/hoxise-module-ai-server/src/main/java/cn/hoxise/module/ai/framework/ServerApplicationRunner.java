package cn.hoxise.module.ai.framework;

import cn.hoxise.module.ai.service.movie.AiMovieVectorStoreService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * 启动后
 *
 * @author hoxise
 * @since 2026/1/28 下午3:08
 */
@Component
@Slf4j
public class ServerApplicationRunner implements ApplicationRunner {

    @Value("${hoxise.pushVectorStore}")
    private boolean pushVectorStore;

    @Resource
    private AiMovieVectorStoreService aiMovieVectorStoreService;

    @Override
    public void run(ApplicationArguments args) {
        if(pushVectorStore){
            //推送影视向量数据
            aiMovieVectorStoreService.pushVectorStore();
        }
    }

}
