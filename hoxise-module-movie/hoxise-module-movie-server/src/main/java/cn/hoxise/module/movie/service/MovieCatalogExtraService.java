package cn.hoxise.module.movie.service;

import cn.hoxise.module.movie.controller.movie.vo.MovieExtraCheckVO;
import cn.hoxise.module.movie.dal.entity.MovieCatalogExtraDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.function.Function;

/**
 * MovieCatalogExtraService
 *
 * @author Hoxise
 * @since 2026/04/06
 */
public interface MovieCatalogExtraService extends IService<MovieCatalogExtraDO> {

    /**
     * 判断是否有拓展信息
     *
     * @param catalogId 目录 ID
     * @return 拓展信息检查VO
     * @author hoxise
     * @since 2026/04/09 20:32:49
     */
    MovieExtraCheckVO hasInfo(Long catalogId);

    /**
     * 获取拓展信息
     *
     * @param catalogId 目录 ID
     * @param secret 密钥
     * @param function 获取指定字段的函数
     * @return 指定字段的数据
     * @author hoxise
     * @since 2026/04/10 08:46:50
     */
    <T> T getExtraInfo(Long catalogId, String secret, Function<MovieCatalogExtraDO, T> function);
}
