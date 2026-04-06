package cn.hoxise.module.movie.service;

import cn.hoxise.module.movie.dal.entity.MovieCatalogExtraDO;
import cn.hoxise.module.movie.dal.mapper.MovieCatalogExtraMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * MovieCatalogExtraService 实现类
 *
 * @author Hoxise
 * @since 2026/04/06
 */
@Service
public class MovieCatalogExtraServiceImpl extends ServiceImpl<MovieCatalogExtraMapper, MovieCatalogExtraDO>
        implements MovieCatalogExtraService {

    @Override
    public String getPlayUrl(Long catalogId) {
        MovieCatalogExtraDO extra = this.getOne(Wrappers.lambdaQuery(MovieCatalogExtraDO.class)
                .eq(MovieCatalogExtraDO::getCatalogId, catalogId));
        return extra.getPlayUrl();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdatePlayUrl(Long catalogId, String playUrl) {
        MovieCatalogExtraDO extra = this.getOne(Wrappers.lambdaQuery(MovieCatalogExtraDO.class)
                .eq(MovieCatalogExtraDO::getCatalogId, catalogId));

        if (extra == null) {
            // 不存在则新建
            extra = MovieCatalogExtraDO.builder()
                    .catalogId(catalogId)
                    .playUrl(playUrl)
                    .updateTime(LocalDateTime.now())
                    .build();
            this.save(extra);
        } else {
            // 存在则更新
            extra.setPlayUrl(playUrl);
            extra.setUpdateTime(LocalDateTime.now());
            this.updateById(extra);
        }
    }

}
