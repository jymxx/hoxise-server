package cn.hoxise.self.biz.service.movie;

import cn.hoxise.common.base.pojo.PageResult;
import cn.hoxise.self.biz.controller.movie.dto.MovieSimpleQueryDTO;
import cn.hoxise.self.biz.controller.movie.vo.MovieSimpleVO;
import cn.hoxise.self.biz.controller.movie.vo.MovieStatVO;
import cn.hoxise.self.biz.convert.MovieCatalogConvert;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiDO;
import cn.hoxise.self.biz.service.tmdb.MovieDbDO;
import cn.hoxise.self.biz.pojo.constants.MovieConstants;
import cn.hoxise.self.biz.pojo.constants.MovieRedisConstants;
import cn.hoxise.self.biz.pojo.enums.MovieCountyEnum;
import cn.hoxise.self.biz.service.tmdb.MovieDbService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.self.biz.dal.entity.MovieCatalogDO;
import cn.hoxise.self.biz.dal.mapper.MovieCatalogMapper;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author Hoxise
* @description 针对表【movie_catalog】的数据库操作Service实现
* @createDate 2025-12-22 07:34:01
*/
@Service
public class MovieCatalogServiceImpl extends ServiceImpl<MovieCatalogMapper, MovieCatalogDO>
    implements MovieCatalogService{

    @Resource private MovieDbBangumiService movieDbBangumiService;

    @Override
    public Page<MovieCatalogDO> page(MovieSimpleQueryDTO queryDTO){
        Page<MovieCatalogDO> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        return baseMapper.selectPage(page,Wrappers.lambdaQuery(MovieCatalogDO.class)
                .eq(StrUtil.isNotBlank(queryDTO.getDirectory()),MovieCatalogDO::getDirectory, queryDTO.getDirectory()));
    }

    @Override
    public PageResult<MovieSimpleVO> listPageContainDB(MovieSimpleQueryDTO queryDTO) {
        //目录数据
        Page<MovieCatalogDO> page = page(queryDTO);
        List<MovieCatalogDO> records = page.getRecords();
        List<MovieSimpleVO> convert = MovieCatalogConvert.INSTANCE.convert(records);

        addDBData(convert);
        return new PageResult<>(convert, page.getTotal());
    }

    @Override
    @Cacheable(
            value = MovieRedisConstants.MOVIE_LIBRARY_KEY,
            key = "{#queryDTO.pageNum, #queryDTO.directory}"
    )
    public PageResult<MovieSimpleVO> libraryDBCache(MovieSimpleQueryDTO queryDTO) {
        int batchSize = 50;//一次拉五十条下去
        queryDTO.setPageSize(batchSize);
        return listPageContainDB(queryDTO);
    }

    @Override
    @Cacheable(value = MovieRedisConstants.MOVIE_STAT_KEY)
    public MovieStatVO statCount(){
        MovieStatVO result = new MovieStatVO();
        result.setTotalCount(this.count());
        result.setTotalAnime(this.count(Wrappers.lambdaQuery(MovieCatalogDO.class).eq(MovieCatalogDO::getDirectory,"动漫")));
        result.setTotalAnimeMovie(this.count(Wrappers.lambdaQuery(MovieCatalogDO.class).eq(MovieCatalogDO::getDirectory,"动漫电影")));
        result.setTotalTV(this.count(Wrappers.lambdaQuery(MovieCatalogDO.class).in(MovieCatalogDO::getDirectory,"日剧")));
        return result;
    }

    @Override
    public List<MovieSimpleVO> randomQuery(Integer limit){
        limit = Math.min(limit, 20);

        // MySQL使用RAND()
        List<MovieCatalogDO> catalogList = this.list(
                Wrappers.lambdaQuery(MovieCatalogDO.class).last("order by RAND() LIMIT " + limit)
        );

        List<MovieSimpleVO> result = MovieCatalogConvert.INSTANCE.convert(catalogList);
        addDBData(result);
        return result;
    }

    @Override
    public List<MovieSimpleVO> LastUpdate(){
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(2);
        Page<MovieCatalogDO> page = this.page(new Page<>(1, 20)
                , Wrappers.lambdaQuery(MovieCatalogDO.class).ge(MovieCatalogDO::getLastModifyTime, threeMonthsAgo)
                        .orderByDesc(MovieCatalogDO::getLastModifyTime));

        List<MovieSimpleVO> result = MovieCatalogConvert.INSTANCE.convert(page.getRecords());

        // 添加DB数据
        addDBData(result);

        return result;
    }

    /**
     * @Author: hoxise
     * @Description: 追加DB数据-TMDB
     * @Date: 2025/12/22 下午4:44
     */
    private void addDBData(List<MovieSimpleVO> simpleVOS){
        if (simpleVOS.isEmpty()){
            return;
        }
        //DB数据
        List<MovieDbBangumiDO> movieDbs = movieDbBangumiService.listByCatalogId(simpleVOS.stream().map(MovieSimpleVO::getId).toList());

        Map<Long, MovieDbBangumiDO> movieDbMap = movieDbs.stream()
                .collect(Collectors.toMap(MovieDbBangumiDO::getCatalogid, m -> m));
        //赋值
        simpleVOS.forEach(f->{
            MovieDbBangumiDO movieDbDO = movieDbMap.get(f.getId());
            if (movieDbDO == null){
                return;
            }
            //清理字符
            f.setName(MovieConstants.MOVIE_CLEAN_PATTERN.matcher(f.getName()).replaceAll(""));

            f.setSubjectId(movieDbDO.getBangumiId());
            f.setPosterUrl(movieDbDO.getPosterUrl());
            f.setRating(movieDbDO.getRating());
            f.setReleaseYear(movieDbDO.getReleaseDate() == null ? null : movieDbDO.getReleaseDate().getYear());
            f.setPlatform(movieDbDO.getPlatform());
            f.setSubjectType(movieDbDO.getSubjectType());
            f.setMetaTags(movieDbDO.getMetaTags());
        });
    }

    @Override
    public List<MovieCatalogDO> listNotIn(Collection<Long> notInIds){
        return this.list(Wrappers.lambdaQuery(MovieCatalogDO.class)
                .notIn(notInIds != null && !notInIds.isEmpty(),MovieCatalogDO::getId, notInIds));
    }



}




