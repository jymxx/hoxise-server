package cn.hoxise.api.config;

import cn.hoxise.common.framework.utils.RedisUtil;
import cn.hoxise.self.ai.service.AiVectorStoreService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * ApplicationRunnerImpl
 *
 * @author 项目启动后执行
 * @since 2026/01/14 06:14:42
 */
@Component
@Slf4j
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${project.isClearCache}")
    @Schema(name = "项目启动后是否清空redis缓存")
    private Boolean isClearRedis;

    @Resource private RedisUtil redisUtil;

    @Resource private AiVectorStoreService vectorStoreService;

    @Override
    public void run(ApplicationArguments args) {
        printInfo();
        clearRedisCache();
        //向redis推送AI向量数据
        vectorStoreService.pushVectorStore();
    }

    private void printInfo() {
        String art = """
                    　 ∧_∧
                    （´・ω・)つ━☆・*。
                    ⊂　　 ノ 　　　・゜+.
                    　しーＪ　　　°。+ *´¨)
                    　　　　　　　　　.· ´¸.·*´¨)
                    　　　　　　　　　　(¸.·´ (¸.·'* ☆
                    """;
        String coloredArt = AnsiOutput.toString(AnsiColor.BRIGHT_MAGENTA, art);
        String box = AnsiOutput.toString(
                AnsiColor.BRIGHT_BLUE, """
                ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
                ┃                                             ┃
                ┃""",
                AnsiColor.YELLOW, "      ✧･ﾟ  ",
                AnsiColor.BRIGHT_CYAN, " Hoxise API 起動完了！ ",
                AnsiColor.YELLOW, "  ﾟ･✧        ",
                AnsiColor.BRIGHT_BLUE, """
                ┃
                ┃                                             ┃
                ┃           \s""",
                AnsiColor.BRIGHT_GREEN, " サービス開始しました！\u200A\u200A\u200A              ",
                AnsiColor.BRIGHT_BLUE,"""
                ┃
                ┃             \s""",
                AnsiColor.BRIGHT_YELLOW, "待機中 (◠‿◠)✧",
                AnsiColor.BRIGHT_BLUE, """
                                  ┃
                ┃                                             ┃
                ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
                """
        );

        log.info("\n{}{}", coloredArt, box);
    }

    private void clearRedisCache() {
        if(isClearRedis){
            redisUtil.clear();
            log.info("---项目启动后清除redis缓存完成.");
        }
    }

}
