package cn.hoxise.module.movie.service;

import cn.hoxise.module.movie.controller.movie.vo.AllowAccessUserInfoVO;

/**
 * 影视数据
 *
 * @author hoxise
 * @since 2026/4/2 下午11:28
 */
public interface MovieDataService {

    /**
     * 验证数据是否允许访问
     *
     * @param userId 用户ID
     * @return boolean
     * @author hoxise
     * @since 2026/04/02 23:45:27
     */
    AllowAccessUserInfoVO allowAccess(Long userId);
}
