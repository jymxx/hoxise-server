package cn.hoxise.self.biz.service.movie.bangumi;

import cn.hoxise.self.biz.pojo.dto.BangumiSearchSubjectResponse;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiInfoboxDO;
import cn.hoxise.self.biz.dal.mapper.MovieDbBangumiInfoboxMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author Hoxise
* @description 针对表【movie_db_bangumi_infobox(Bangumi 的infobox)】的数据库操作Service实现
* @createDate 2025-12-23 10:47:43
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




