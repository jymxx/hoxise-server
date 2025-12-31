package cn.hoxise.api;

import cn.hoxise.common.file.api.FileStorageApi;
import cn.hoxise.common.file.pojo.enums.FileTypeEnum;
import cn.hoxise.self.biz.service.movie.bangumi.MovieBangumiManageService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Author hoxise
 * @Description: test
 * @Date 2025-12-13 上午1:25
 */
@SpringBootTest
@TestPropertySource(locations = "file:../.env")
public class HoxiseTest {

    @Resource
    private MovieBangumiManageService movieBangumiManageService;

    @Autowired
    FileStorageApi fileStorageApi;

    @Test
    public void test(){

        String presignedUrl = fileStorageApi.getPresignedUrlCache("", FileTypeEnum.img);
        System.out.println(presignedUrl);
    }

//############################# movie批量扫描等 #################################
    @Test
    public void scanNas() {
        //扫描本地盘
        movieBangumiManageService.dirScan( false);
    }

    @Test
    public void matchingDB() {
        //匹配基本db
        movieBangumiManageService.allMatchingBangumi(false);
    }

    @Test
    public void matchingCharacters() {
        //匹配角色
        movieBangumiManageService.matchCharacters();
    }
    @Test
    public void matchingEpisode(){
        //匹配章节信息
        movieBangumiManageService.matchEpisode();
    }

}
