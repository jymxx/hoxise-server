package cn.hoxise.module.movie.service;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.module.movie.controller.movie.vo.MovieExtraCheckVO;
import cn.hoxise.module.movie.dal.entity.MovieCatalogExtraDO;
import cn.hoxise.module.movie.dal.mapper.MovieCatalogExtraMapper;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.function.Function;

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
    public MovieExtraCheckVO hasInfo(Long catalogId){
        MovieCatalogExtraDO extra = this.getOne(Wrappers.lambdaQuery(MovieCatalogExtraDO.class)
                .eq(MovieCatalogExtraDO::getCatalogId, catalogId));
        if (BeanUtil.isEmpty(extra)) {
            new MovieExtraCheckVO();
        }

        return MovieExtraCheckVO.builder()
                .hasPlayUrl(StrUtil.isNotBlank(extra.getPlayUrl()))
                .hasResourceUrl(StrUtil.isNotBlank(extra.getExtraData()))
                .hasSecret(StrUtil.isNotBlank(extra.getSecret()))
                .build();
    }

    @Override
    public String getExtraInfo(Long catalogId, String secret , Function<MovieCatalogExtraDO, String> function) {
        MovieCatalogExtraDO extra = this.getOne(Wrappers.lambdaQuery(MovieCatalogExtraDO.class)
                .eq(MovieCatalogExtraDO::getCatalogId, catalogId));
        if (StrUtil.isNotBlank(extra.getSecret()) && !extra.getSecret().equals(secret)){
            throw new ServiceException("获取密码错误");
        }
        return function.apply(extra);
    }


}
