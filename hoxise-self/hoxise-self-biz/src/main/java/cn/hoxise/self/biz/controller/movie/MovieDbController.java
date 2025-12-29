package cn.hoxise.self.biz.controller.movie;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.file.api.FileStorageApi;
import cn.hoxise.self.biz.controller.movie.vo.MovieCharactersVO;
import cn.hoxise.self.biz.controller.movie.vo.MovieDetailVO;
import cn.hoxise.self.biz.controller.movie.vo.MovieEpisodesVO;
import cn.hoxise.self.biz.service.movie.bangumi.MovieDbBangumiCharacterService;
import cn.hoxise.self.biz.service.movie.bangumi.MovieDbBangumiEpisodeService;
import cn.hoxise.self.biz.service.movie.bangumi.MovieDbBangumiService;
import cn.hoxise.system.api.dict.DictApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author hoxise
 * @Description: 影视db数据控制类
 * @Date 2025-12-23 下午3:19
 */
@Tag(name = "影视db数据控制类")
@RestController
@RequestMapping("/movie/db")
public class MovieDbController {

    @Resource private MovieDbBangumiService movieDbBangumiService;

    @Resource private MovieDbBangumiCharacterService movieDbBangumiCharacterService;

    @Resource private MovieDbBangumiEpisodeService movieDbBangumiEpisodeService;

    @Resource private FileStorageApi fileStorageApi;;

    @Operation(summary = "获取影视详情")
    @GetMapping("/detail")
    public CommonResult<MovieDetailVO> detail(Long catalogid){
        return CommonResult.success(movieDbBangumiService.detailByCatalogId(catalogid));
    }

    @Operation(summary = "获取角色信息")
    @GetMapping("/characters")
    public CommonResult<List<MovieCharactersVO>> characters(Long catalogid){
        return CommonResult.success(movieDbBangumiCharacterService.getCharacters(catalogid));
    }

    @Operation(summary = "获取章节信息")
    @GetMapping("/episodes")
    public CommonResult<List<MovieEpisodesVO>> episodes(Long catalogid){
        return CommonResult.success(movieDbBangumiEpisodeService.listVOByCatalogId(catalogid));
    }

    //后续可能删掉播放功能 临时放着
    @Resource private DictApi dictApi;
    @Operation(summary = "获取播放地址")
    @GetMapping("/playerUrl")
    public CommonResult<String> playerUrl(){
        return CommonResult.success(fileStorageApi.getPresignedUrlCache(dictApi.getByKey("movie_player_url")));
    }
}
