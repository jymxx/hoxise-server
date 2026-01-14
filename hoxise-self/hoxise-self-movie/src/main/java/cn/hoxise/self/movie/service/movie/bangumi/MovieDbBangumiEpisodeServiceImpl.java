package cn.hoxise.self.movie.service.movie.bangumi;

import cn.hoxise.self.movie.controller.movie.vo.MovieEpisodesVO;
import cn.hoxise.self.movie.convert.MovieEpisodeConvert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.self.movie.dal.entity.MovieDbBangumiEpisodeDO;
import cn.hoxise.self.movie.dal.mapper.MovieDbBangumiEpisodeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * bangumi剧集表服务实现
 *
 * @author Hoxise
 * @since 2026/01/14 15:15:30
 */
@Service
public class MovieDbBangumiEpisodeServiceImpl extends ServiceImpl<MovieDbBangumiEpisodeMapper, MovieDbBangumiEpisodeDO>
    implements MovieDbBangumiEpisodeService{


    @Override
    public List<MovieDbBangumiEpisodeDO> listByCatalogId(Long catalogId) {
        return this.list(Wrappers.lambdaQuery(MovieDbBangumiEpisodeDO.class).eq(MovieDbBangumiEpisodeDO::getCatalogid, catalogId));
    }

    @Override
    public List<MovieEpisodesVO> listVoByCatalogId(Long catalogId) {
        return MovieEpisodeConvert.INSTANCE.convert(listByCatalogId(catalogId));
    }
}




