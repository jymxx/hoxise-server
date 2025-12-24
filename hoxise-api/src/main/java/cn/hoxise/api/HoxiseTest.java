package cn.hoxise.api;

import cn.hoxise.self.biz.service.movie.MovieManageService;
import cn.hoxise.self.biz.utils.BangumiUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author hoxise
 * @Description: test
 * @Date 2025-12-13 上午1:25
 */
@SpringBootTest(classes = HoxiseWebApplication.class)
public class HoxiseTest {

    @Resource
    private MovieManageService movieManageService;

    @Test
    public void test(){
        movieManageService.matchEpisode();
    }

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


}
