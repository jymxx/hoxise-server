package cn.hoxise.self.movie.service.movie.bangumi;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.self.movie.dal.entity.MovieDbBangumiInfoboxDO;
import cn.hoxise.self.movie.dal.mapper.MovieDbBangumiInfoboxMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 信息框服务实现类
 *
 * @author Hoxise
 * @since 2026/01/14 15:17:10
 */
@Service
public class MovieDbBangumiInfoboxServiceImpl extends ServiceImpl<MovieDbBangumiInfoboxMapper, MovieDbBangumiInfoboxDO>
    implements MovieDbBangumiInfoboxService{


    @Override
    public void removeByCatalogId(Long catalogid) {
        this.remove(Wrappers.<MovieDbBangumiInfoboxDO>lambdaQuery().eq(MovieDbBangumiInfoboxDO::getCatalogid, catalogid));
    }

    @Override
    public List<MovieDbBangumiInfoboxDO> getByCatalogId(Long catalogid) {
        return this.list(Wrappers.<MovieDbBangumiInfoboxDO>lambdaQuery().eq(MovieDbBangumiInfoboxDO::getCatalogid, catalogid));
    }




}




