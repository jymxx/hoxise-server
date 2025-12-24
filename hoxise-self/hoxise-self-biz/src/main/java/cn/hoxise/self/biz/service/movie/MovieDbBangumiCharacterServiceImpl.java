package cn.hoxise.self.biz.service.movie;

import cn.hoxise.self.biz.controller.movie.vo.MovieCharactersVO;
import cn.hoxise.self.biz.convert.MovieCharacterConvert;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiActorDO;
import cn.hoxise.self.biz.pojo.dto.BangumiCharacterResponse;
import cn.hoxise.self.biz.utils.BangumiUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.self.biz.dal.entity.MovieDbBangumiCharacterDO;
import cn.hoxise.self.biz.dal.mapper.MovieDbBangumiCharacterMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author Hoxise
* @description 针对表【movie_db_bangumi_character(电影数据库角色表)】的数据库操作Service实现
* @createDate 2025-12-23 16:43:28
*/
@Service
public class MovieDbBangumiCharacterServiceImpl extends ServiceImpl<MovieDbBangumiCharacterMapper, MovieDbBangumiCharacterDO>
    implements MovieDbBangumiCharacterService{

    @Resource private MovieDbBangumiActorService movieDbBangumiActorService;

    @Override
    public MovieDbBangumiCharacterDO getByCharacterId(Long characterId) {
        return this.getOne(Wrappers.lambdaQuery(MovieDbBangumiCharacterDO.class).eq(MovieDbBangumiCharacterDO::getCharacterId, characterId));
    }

    @Override
    public List<MovieCharactersVO> getCharacters(Long catalogId) {
        if (catalogId == null){
            return Collections.emptyList();
        }
        //从数据库查角色信息
        List<MovieDbBangumiCharacterDO> list = this.list(Wrappers.lambdaQuery(MovieDbBangumiCharacterDO.class)
                .eq(MovieDbBangumiCharacterDO::getCatalogid, catalogId));
        if (list.isEmpty()){
            return Collections.emptyList();
        }

        //---暂时不查CV信息 前端不需要

//        //留一个映射
//        Map<Long, List<String>> characterMapActors = list.stream()
//                .collect(Collectors.toMap(MovieDbBangumiCharacterDO::getCharacterId, MovieDbBangumiCharacterDO::getActors));
//        //拿到所有演员id去查出数据
//        List<String> actorIds = list.stream().map(MovieDbBangumiCharacterDO::getActors)
//                .flatMap(List::stream)
//                .distinct()
//                .toList();
//        List<MovieDbBangumiActorDO> actorDOS = movieDbBangumiActorService.listByIds(actorIds);

        List<MovieCharactersVO> convert = MovieCharacterConvert.INSTANCE.convert(list);
        //演员信息添加进去
//        convert.forEach(c->{
//            List<String> longs = characterMapActors.get(c.getCharacterId());
//            c.setActors(actorDOS.stream().filter(actor -> longs.contains(actor.getActorId().toString())).toList());
//        });

        return convert;
    }





}




