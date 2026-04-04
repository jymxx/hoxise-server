package cn.hoxise.module.movie.dal.mapper;

import cn.hoxise.module.movie.controller.movie.dto.MovieSimpleQueryDTO;
import cn.hoxise.module.movie.dal.entity.BangumiDbDO;
import cn.hoxise.module.movie.dal.entity.MovieCatalogDO;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

/**
 * MovieCatalogMapper
 *
 * @author Hoxise
 * @since 2026/01/14 14:57:55
 */
public interface MovieCatalogMapper extends MPJBaseMapper<MovieCatalogDO> {

    default Page<MovieCatalogDO> pageList(MovieSimpleQueryDTO queryDTO){
        MPJLambdaWrapper<MovieCatalogDO> wrapper = new MPJLambdaWrapper<>(MovieCatalogDO.class)
                .leftJoin(BangumiDbDO.class, BangumiDbDO::getBangumiId, MovieCatalogDO::getBangumiId)
                .selectAll(MovieCatalogDO.class)
                .eq(MovieCatalogDO::getUserid, queryDTO.getUserid())
                .eq(StrUtil.isNotBlank(queryDTO.getDirectory()), MovieCatalogDO::getDirectory, queryDTO.getDirectory())
                .isNull(queryDTO.getNotMatched() != null && queryDTO.getNotMatched(), MovieCatalogDO::getBangumiId)
                .like(StrUtil.isNotBlank(queryDTO.getKeyword()), MovieCatalogDO::getName, queryDTO.getKeyword());
        if (queryDTO.getOrderBy() != null){
            switch (queryDTO.getOrderBy()){
                case "createTime": // 创建时间
                    wrapper.orderBy(true,queryDTO.getIsAsc(), MovieCatalogDO::getCreateTime);
                    break;
                case "totalSize": // 总大小
                    wrapper.orderBy(true,queryDTO.getIsAsc(), MovieCatalogDO::getTotalSize);
                    break;
                case "releaseDate": // 发布时间
                    wrapper.orderBy(true,queryDTO.getIsAsc(), BangumiDbDO::getReleaseDate);
                    break;
                default:
                    wrapper.orderBy(true,queryDTO.getIsAsc(), MovieCatalogDO::getId);
                    break;
            }
        }
        return selectPage(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()), wrapper);
    }

}




