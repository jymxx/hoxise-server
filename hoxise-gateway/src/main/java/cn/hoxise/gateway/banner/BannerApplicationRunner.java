package cn.hoxise.gateway.banner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.stereotype.Component;

/**
 * BannerApplicationRunner
 *
 * @author hoxise
 * @since 2026/01/14 06:14:42
 */
@Slf4j
@Component
public class BannerApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        printInfo();
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

}
