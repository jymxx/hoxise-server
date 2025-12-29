package cn.hoxise.api;

import cn.hoxise.self.biz.service.movie.bangumi.MovieBangumiManageService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

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
    VectorStore vectorStore;

    @Test
    public void test(){
        List <Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));
        Document doc = new Document("your-content", Map.of("customId", "your-id", "other-meta", "value"));
// Add the documents to Redis
        vectorStore.add(documents);

// Retrieve documents similar to a query
        List<Document> results = this.vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());
        System.out.println( results);
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
