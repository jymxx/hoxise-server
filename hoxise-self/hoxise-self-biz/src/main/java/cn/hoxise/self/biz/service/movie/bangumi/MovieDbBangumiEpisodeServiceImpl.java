package cn.hoxise.self.biz.service.movie.bangumi;

import cn.hoxise.self.biz.controller.movie.vo.MovieEpisodesVO;
import cn.hoxise.self.biz.convert.MovieEpisodeConvert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiEpisodeDO;
import cn.hoxise.self.biz.dal.mapper.MovieDbBangumiEpisodeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Hoxise
* @description 针对表【movie_db_bangumi_episode(Bangumi剧集表)】的数据库操作Service实现
* @createDate 2025-12-24 02:42:23
*/
@Service
public class MovieDbBangumiEpisodeServiceImpl extends ServiceImpl<MovieDbBangumiEpisodeMapper, MovieDbBangumiEpisodeDO>
    implements MovieDbBangumiEpisodeService{


    @Override
    public List<MovieDbBangumiEpisodeDO> listByCatalogId(Long catalogId) {
        return this.list(Wrappers.lambdaQuery(MovieDbBangumiEpisodeDO.class).eq(MovieDbBangumiEpisodeDO::getCatalogid, catalogId));
    }

    @Override
    public List<MovieEpisodesVO> listVOByCatalogId(Long catalogId) {
        return MovieEpisodeConvert.INSTANCE.convert(listByCatalogId(catalogId));
    }
}




