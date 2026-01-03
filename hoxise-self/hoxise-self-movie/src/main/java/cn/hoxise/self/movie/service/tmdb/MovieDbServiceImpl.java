package cn.hoxise.self.movie.service.tmdb;

import cn.hoxise.self.movie.dal.mapper.MovieDbMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
* @author Hoxise
* @description 针对表【movie_db】的数据库操作Service实现
* @createDate 2025-12-22 07:34:01
*/
@Service
public class MovieDbServiceImpl extends ServiceImpl<MovieDbMapper, MovieDbDO>
    implements MovieDbService{


    @Resource private MovieDbService movieDbService;

    @Override
    public List<Long> getCatalogIdList(){
        return  this.listMaps(Wrappers.lambdaQuery(MovieDbDO.class)
                        .select(MovieDbDO::getCatalogid))
                .stream()
                .map(map -> (Long) map.get("catalogid"))
                .distinct()
                .toList();
    }
    @Override
    public List<MovieDbDO> listByCatalogId(Collection<Long> catalogIds) {
        return this.list(Wrappers.lambdaQuery(MovieDbDO.class)
                .in(MovieDbDO::getCatalogid, catalogIds));
    }


}




