package cn.hoxise.module.movie.service;

import cn.hoxise.common.base.exception.ServiceException;
import cn.hoxise.module.movie.controller.movie.vo.MovieExtraCheckVO;
import cn.hoxise.module.movie.dal.entity.MovieCatalogExtraDO;
import cn.hoxise.module.movie.dal.mapper.MovieCatalogExtraMapper;
import cn.hoxise.module.movie.enums.MovieResourceTypeEnum;
import cn.hoxise.module.movie.controller.movie.dto.MovieCatalogExtraSaveDTO;
import cn.hoxise.module.movie.controller.movie.dto.MovieCatalogExtraUpdateDTO;
import cn.hoxise.module.system.api.file.FileApi;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MovieCatalogExtraService 实现类
 *
 * @author Hoxise
 * @since 2026/04/06
 */
@Service
public class MovieCatalogExtraServiceImpl extends ServiceImpl<MovieCatalogExtraMapper, MovieCatalogExtraDO>
        implements MovieCatalogExtraService {

    @Resource private FileApi fileApi;

    @Override
    @Transactional
    public void saveExtra(MovieCatalogExtraSaveDTO dto) {
        // OTHER_CLOUD 类型必须传 otherCloudUrl，其它类型必须传 fileId
        if (dto.getResourceType() == MovieResourceTypeEnum.OTHER_CLOUD) {
            Assert.notBlank(dto.getOtherCloudUrl(), "云盘资源必须提供链接地址");
        } else {
            Assert.notBlank(dto.getFileId(), "OSS资源必须提供文件ID");
            fileApi.bindFile(dto.getFileId());
        }
        MovieCatalogExtraDO extra = MovieCatalogExtraDO.builder()
                .catalogId(dto.getCatalogId())
                .fileId(dto.getFileId())
                .resourceType(dto.getResourceType())
                .otherCloudUrl(dto.getOtherCloudUrl())
                .showName(dto.getShowName())
                .sort(dto.getSort())
                .secret(dto.getSecret())
                .build();
        this.save(extra);
    }

    @Override
    @Transactional
    public void updateExtra(MovieCatalogExtraUpdateDTO dto) {
        MovieCatalogExtraDO one = getById(dto.getId());
        Assert.notNull(one, "资源不存在");
        LambdaUpdateWrapper<MovieCatalogExtraDO> wrapper = Wrappers.lambdaUpdate(MovieCatalogExtraDO.class)
                .set(dto.getSort() != null,MovieCatalogExtraDO::getSort, dto.getSort())
                .set(StrUtil.isNotBlank(dto.getShowName()),MovieCatalogExtraDO::getShowName, dto.getShowName())
                .set(StrUtil.isNotBlank(dto.getOtherCloudUrl()) , MovieCatalogExtraDO::getOtherCloudUrl, dto.getOtherCloudUrl())
                .eq(MovieCatalogExtraDO::getId, dto.getId());
        this.update(wrapper);
    }

    @Override
    @Transactional
    public void deleteExtra(Long id) {
        MovieCatalogExtraDO one = getById(id);
        fileApi.deleteFile(one.getFileId()); // 删除文件
        this.removeById(id);
    }

    @Override
    public List<MovieExtraCheckVO> hasInfo(Long catalogId) {
        List<MovieCatalogExtraDO> extraList = this.list(Wrappers.lambdaQuery(MovieCatalogExtraDO.class)
                .eq(MovieCatalogExtraDO::getCatalogId, catalogId));

        return extraList.stream()
                .map(extra -> MovieExtraCheckVO.builder()
                        .id(extra.getId())
                        .catalogId(extra.getCatalogId())
                        .resourceType(extra.getResourceType())
                        .showName(extra.getShowName())
                        .sort(extra.getSort())
                        .hasSecret(StrUtil.isNotBlank(extra.getSecret()))
                        .build())
                .toList();
    }

    @Override
    public String getResourceUrl(Long extraId, String secret) {
        MovieCatalogExtraDO extra = this.getById(extraId);
        if (extra == null) {
            throw new ServiceException("资源不存在");
        }

        // 如果记录设置了密钥，则需要校验
        if (StrUtil.isNotBlank(extra.getSecret())) {
            if (StrUtil.isBlank(secret)) {
                throw new ServiceException("该资源需要密码才能访问");
            }
            if (!extra.getSecret().equals(secret)) {
                throw new ServiceException("密码错误");
            }
        }

        // OTHER_CLOUD 类型直接返回云盘地址，其余类型通过 fileApi 获取预览地址
        if (extra.getResourceType() == MovieResourceTypeEnum.OTHER_CLOUD) {
            return extra.getOtherCloudUrl();
        }
        return fileApi.getAccessUrl(extra.getFileId()).getCheckedData();
    }
}
