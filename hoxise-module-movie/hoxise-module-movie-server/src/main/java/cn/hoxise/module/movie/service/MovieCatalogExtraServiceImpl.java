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
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map;

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
        // 云盘资源必须传地址，其它类型必须传 fileId
        if (dto.getResourceType() == MovieResourceTypeEnum.CLOUD_DRIVE) {
            Assert.notBlank(dto.getCloudDriveUrl(), "云盘资源必须提供链接地址");
        } else {
            Assert.notBlank(dto.getFileId(), "OSS资源必须提供文件ID");
            fileApi.bindFile(dto.getFileId());
        }
        MovieCatalogExtraDO extra = MovieCatalogExtraDO.builder()
                .catalogId(dto.getCatalogId())
                .fileId(dto.getFileId())
                .resourceType(dto.getResourceType())
                .cloudDriveUrl(dto.getCloudDriveUrl())
                .showName(dto.getShowName())
                .secret(dto.getSecret())
                .build();
        this.save(extra);
    }

    @Override
    @Transactional
    public void updateExtra(MovieCatalogExtraUpdateDTO dto) {
        MovieCatalogExtraDO one = getById(dto.getId());
        Assert.notNull(one, "资源不存在");
        Assert.isFalse(ObjUtil.isAllEmpty( dto.getShowName(), dto.getCloudDriveUrl()), "参数校验异常");
        LambdaUpdateWrapper<MovieCatalogExtraDO> wrapper = Wrappers.lambdaUpdate(MovieCatalogExtraDO.class)
                .set(StrUtil.isNotBlank(dto.getShowName()),MovieCatalogExtraDO::getShowName, dto.getShowName())
                .set(StrUtil.isNotBlank(dto.getCloudDriveUrl()) , MovieCatalogExtraDO::getCloudDriveUrl, dto.getCloudDriveUrl())
                .eq(MovieCatalogExtraDO::getId, dto.getId());

        baseMapper.update(new MovieCatalogExtraDO(), wrapper);
    }

    @Override
    @Transactional
    public void deleteExtra(Long id) {
        MovieCatalogExtraDO one = getById(id);
        if (StrUtil.isNotBlank(one.getFileId())){
            fileApi.deleteFile(one.getFileId()); // 删除文件
        }
        this.removeById(id);
    }

    @Override
    public List<MovieExtraCheckVO> hasInfo(Long catalogId) {
        List<MovieCatalogExtraDO> extraList = this.list(Wrappers.lambdaQuery(MovieCatalogExtraDO.class)
                .eq(MovieCatalogExtraDO::getCatalogId, catalogId));

        // 按 视频 > 资源文件 > 云盘链接 排序
        Map<MovieResourceTypeEnum, Integer> order = Map.of(
                MovieResourceTypeEnum.VIDEO, 0,
                MovieResourceTypeEnum.RESOURCE_FILE, 1,
                MovieResourceTypeEnum.CLOUD_DRIVE, 2
        );
        extraList.sort(Comparator.comparingInt((MovieCatalogExtraDO e) -> order.getOrDefault(e.getResourceType(), 99))
                .thenComparing(MovieCatalogExtraDO::getId));

        return extraList.stream()
                .map(extra -> MovieExtraCheckVO.builder()
                        .id(extra.getId())
                        .catalogId(extra.getCatalogId())
                        .resourceType(extra.getResourceType())
                        .showName(extra.getShowName())
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
        if (extra.getResourceType() == MovieResourceTypeEnum.CLOUD_DRIVE) {
            return extra.getCloudDriveUrl();
        }
        return fileApi.getDownloadUrl(extra.getFileId()).getCheckedData();
    }
}
