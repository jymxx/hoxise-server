package cn.hoxise.module.movie.controller.movie;

import cn.hoxise.common.base.pojo.CommonResult;
import cn.hoxise.common.file.utils.FileStorageUtil;
import cn.hoxise.module.movie.controller.movie.vo.MovieCharactersVO;
import cn.hoxise.module.movie.controller.movie.vo.MovieDetailVO;
import cn.hoxise.module.movie.controller.movie.vo.MovieEpisodesVO;
import cn.hoxise.module.movie.service.movie.bangumi.BangumiDbCharacterService;
import cn.hoxise.module.movie.service.movie.bangumi.BangumiDbEpisodeService;
import cn.hoxise.module.movie.service.movie.bangumi.BangumiDbService;
import cn.hoxise.module.system.api.dict.DictApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class MovieDbController {

    @Resource private BangumiDbService bangumiDbService;

    @Resource private BangumiDbCharacterService bangumiDbCharacterService;

    @Resource private BangumiDbEpisodeService bangumiDbEpisodeService;

    @Operation(summary = "获取影视详情")
    @GetMapping("/detail")
    public CommonResult<MovieDetailVO> detail(Long catalogid){
        return CommonResult.success(bangumiDbService.detailByCatalogId(catalogid));
    }

    @Operation(summary = "获取角色信息")
    @GetMapping("/characters")
    public CommonResult<List<MovieCharactersVO>> characters(Long catalogid){
        return CommonResult.success(bangumiDbCharacterService.getCharacters(catalogid));
    }

    @Operation(summary = "获取章节信息")
    @GetMapping("/episodes")
    public CommonResult<List<MovieEpisodesVO>> episodes(Long catalogid){
        return CommonResult.success(bangumiDbEpisodeService.listVoByCatalogId(catalogid));
    }

    //后续可能删掉播放功能 临时放着
    @Resource private DictApi dictApi;
    @Operation(summary = "获取播放地址")
    @GetMapping("/playerUrl")
    public CommonResult<String> playerUrl(){
        return CommonResult.success(FileStorageUtil.getAbsoluteUrl(dictApi.getByKey("movie_player_url").getCheckedData()));
    }
}
