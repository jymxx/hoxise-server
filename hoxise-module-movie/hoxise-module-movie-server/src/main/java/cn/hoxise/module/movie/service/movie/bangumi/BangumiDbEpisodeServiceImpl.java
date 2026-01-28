package cn.hoxise.module.movie.service.movie.bangumi;

import cn.hoxise.module.movie.controller.movie.vo.MovieEpisodesVO;
import cn.hoxise.module.movie.convert.MovieEpisodeConvert;
import cn.hoxise.module.movie.dal.entity.BangumiDbEpisodeDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.module.movie.dal.mapper.MovieDbBangumiEpisodeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * bangumi剧集表服务实现
 *
 * @author Hoxise
 * @since 2026/01/14 15:15:30
 */
@Service
public class BangumiDbEpisodeServiceImpl extends ServiceImpl<MovieDbBangumiEpisodeMapper, BangumiDbEpisodeDO>
    implements BangumiDbEpisodeService {


    @Override
    public List<BangumiDbEpisodeDO> listByCatalogId(Long catalogId) {
        return this.list(Wrappers.lambdaQuery(BangumiDbEpisodeDO.class).eq(BangumiDbEpisodeDO::getCatalogid, catalogId));
    }

    @Override
    public List<MovieEpisodesVO> listVoByCatalogId(Long catalogId) {
        return MovieEpisodeConvert.INSTANCE.convert(listByCatalogId(catalogId));
    }
}




