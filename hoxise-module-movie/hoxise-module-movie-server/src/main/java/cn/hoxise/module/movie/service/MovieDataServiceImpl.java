package cn.hoxise.module.movie.service;

import cn.hoxise.module.system.api.user.SystemUserApi;
import cn.hoxise.module.system.api.user.dto.UserInfoRespDTO;
import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 影视数据管理
 *
 * @author hoxise
 * @since 2026/4/2 下午11:28
 */
@Service
public class MovieDataServiceImpl implements MovieDataService{

    @Resource private MovieCatalogService movieCatalogService;

    @Resource private SystemUserApi systemUserApi;

    @Override
    public boolean allowAccess(Long userId) {
        //暂时只校验用户是否存在
        UserInfoRespDTO data = systemUserApi.getUserById(userId).getCheckedData();
        return BeanUtil.isNotEmpty(data);
    }

}
