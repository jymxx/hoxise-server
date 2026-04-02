package cn.hoxise.module.movie.service;

/**
 * 影视数据
 *
 * @author hoxise
 * @since 2026/4/2 下午11:28
 */
public interface MovieDataService {

    /**
     * 判断数据是否允许访问
     *
     * @param userId 用户ID
     * @return boolean
     * @author hoxise
     * @since 2026/04/02 23:45:27
     */
    boolean allowAccess(Long userId);
}
