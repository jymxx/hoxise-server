package cn.hoxise.api;

import cn.hoxise.common.ai.api.OpenAiApi;
import cn.hoxise.self.biz.service.movie.MovieManageService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * @Author hoxise
 * @Description: test
 * @Date 2025-12-13 上午1:25
 */
@SpringBootTest
@TestPropertySource(locations = "file:../.env")
public class HoxiseTest {

    @Resource
    private MovieManageService movieManageService;

    @Resource
    private OpenAiApi openAiApi;

    @Test
    public void test(){
        System.out.println("1");
    }

//############################# movie批量扫描等 #################################
    @Test
    public void scanNas() {
        //扫描本地盘
        movieManageService.dirScan( false);
    }

    @Test
    public void matchingDB() {
        //匹配基本db
        movieManageService.allMatchingBangumi(false);
    }

    @Test
    public void matchingCharacters() {
        //匹配角色
        movieManageService.matchCharacters();
    }
    @Test
    public void matchingEpisode(){
        //匹配章节信息
        movieManageService.matchEpisode();
    }

}
