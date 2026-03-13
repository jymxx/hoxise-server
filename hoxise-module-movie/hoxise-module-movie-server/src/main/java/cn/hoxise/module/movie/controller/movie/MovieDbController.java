package cn.hoxise.module.movie.controller.movie;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.file.core.client.FileStorageClientFactory;
import cn.hoxise.module.movie.controller.movie.vo.MovieCharactersVO;
import cn.hoxise.module.movie.controller.movie.vo.MovieDetailVO;
import cn.hoxise.module.movie.controller.movie.vo.MovieEpisodesVO;
import cn.hoxise.module.movie.service.MovieCatalogService;
import cn.hoxise.module.movie.service.bangumi.BangumiDbCharacterService;
import cn.hoxise.module.movie.service.bangumi.BangumiDbEpisodeService;
import cn.hoxise.module.movie.service.bangumi.BangumiDbService;
import cn.hoxise.module.system.api.dict.DictApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * 影视db数据控制类
 *
 * @author hoxise
 * @since 2026/01/14 14:55:50
 */
@Tag(name = "影视db数据控制类")
@RestController
@RequestMapping("/movie/db")
@Validated
public class MovieDbController {

    @Resource private MovieCatalogService movieCatalogService;

    @Resource private BangumiDbService bangumiDbService;

    @Resource private BangumiDbCharacterService bangumiDbCharacterService;

    @Resource private BangumiDbEpisodeService bangumiDbEpisodeService;

    @Resource private FileStorageClientFactory fileStorageClientFactory;

    @Operation(summary = "获取影视详情")
    @GetMapping("/detail")
    public CommonResult<MovieDetailVO> detail(@NotNull Long catalogId){
        Long bangumiId = movieCatalogService.getBangumiIdByCatalogId(Collections.singleton(catalogId)).getFirst();
        return CommonResult.success(bangumiDbService.detailByBangumiId(bangumiId));
    }

    @Operation(summary = "获取角色信息")
    @GetMapping("/characters")
    public CommonResult<List<MovieCharactersVO>> characters(@NotNull Long catalogId){
        Long bangumiId = movieCatalogService.getBangumiIdByCatalogId(Collections.singleton(catalogId)).getFirst();
        return CommonResult.success(bangumiDbCharacterService.getCharacters(bangumiId));
    }

    @Operation(summary = "获取章节信息")
    @GetMapping("/episodes")
    public CommonResult<List<MovieEpisodesVO>> episodes(@NotNull Long catalogId){
        Long bangumiId = movieCatalogService.getBangumiIdByCatalogId(Collections.singleton(catalogId)).getFirst();
        return CommonResult.success(bangumiDbEpisodeService.listVoByBangumiId(bangumiId));
    }

    //后续可能删掉播放功能 临时放着
    @Resource private DictApi dictApi;
    @Operation(summary = "获取播放地址")
    @GetMapping("/playerUrl")
    public CommonResult<String> playerUrl(){
        return CommonResult.success(fileStorageClientFactory.getDefaultStorage().getPresignedUrl(dictApi.getByKey("movie_player_url").getCheckedData()));
    }
}
