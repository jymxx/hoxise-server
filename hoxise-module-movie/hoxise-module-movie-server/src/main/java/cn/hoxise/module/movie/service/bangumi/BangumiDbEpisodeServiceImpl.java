package cn.hoxise.module.movie.service.bangumi;

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
    public List<BangumiDbEpisodeDO> listByBangumiId(Long bangumiId) {
        return this.list(Wrappers.lambdaQuery(BangumiDbEpisodeDO.class).eq(BangumiDbEpisodeDO::getBangumiId, bangumiId));
    }

    @Override
    public List<MovieEpisodesVO> listVoByBangumiId(Long bangumiId) {
        return MovieEpisodeConvert.INSTANCE.convert(listByBangumiId(bangumiId));
    }

    @Override
    public void removeByBangumiId(Long bangumiId) {
        remove(Wrappers.lambdaQuery(BangumiDbEpisodeDO.class).eq(BangumiDbEpisodeDO::getBangumiId, bangumiId));
    }
}




