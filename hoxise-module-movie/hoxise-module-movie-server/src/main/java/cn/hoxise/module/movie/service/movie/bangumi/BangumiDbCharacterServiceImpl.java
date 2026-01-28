package cn.hoxise.module.movie.service.movie.bangumi;

import cn.hoxise.module.movie.controller.movie.vo.MovieCharactersVO;
import cn.hoxise.module.movie.convert.MovieCharacterConvert;
import cn.hoxise.module.movie.dal.entity.BangumiDbCharacterDO;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hoxise.module.movie.dal.mapper.MovieDbBangumiCharacterMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * bangumi角色管理服务
 *
 * @author Hoxise
 * @since 2026/01/14 15:14:06
 */
@Service
public class BangumiDbCharacterServiceImpl extends ServiceImpl<MovieDbBangumiCharacterMapper, BangumiDbCharacterDO>
    implements BangumiDbCharacterService {

    @Resource private BangumiDbActorService bangumiDbActorService;

    @Override
    public BangumiDbCharacterDO getByCharacterId(Long characterId) {
        return this.getOne(Wrappers.lambdaQuery(BangumiDbCharacterDO.class).eq(BangumiDbCharacterDO::getCharacterId, characterId));
    }

    @Override
    public List<MovieCharactersVO> getCharacters(Long catalogId) {
        Assert.notNull(catalogId, "参数错误");
        //从数据库查角色信息
        List<BangumiDbCharacterDO> list = this.list(Wrappers.lambdaQuery(BangumiDbCharacterDO.class)
                .eq(BangumiDbCharacterDO::getCatalogid, catalogId));
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

    @Override
    public void removeByCatalogId(Long catalogId) {
        this.remove(Wrappers.lambdaQuery(BangumiDbCharacterDO.class).eq(BangumiDbCharacterDO::getCatalogid, catalogId));
    }


}




