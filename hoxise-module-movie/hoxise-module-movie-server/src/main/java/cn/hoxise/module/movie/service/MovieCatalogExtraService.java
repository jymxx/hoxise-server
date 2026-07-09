package cn.hoxise.module.movie.service;

import cn.hoxise.module.movie.controller.movie.vo.MovieExtraCheckVO;
import cn.hoxise.module.movie.dal.entity.MovieCatalogExtraDO;
import cn.hoxise.module.movie.controller.movie.dto.MovieCatalogExtraSaveDTO;
import cn.hoxise.module.movie.controller.movie.dto.MovieCatalogExtraUpdateDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     * @return 拓展信息列表
     * @author hoxise
     * @since 2026/04/09 20:32:49
     */
    List<MovieExtraCheckVO> hasInfo(Long catalogId);

    /**
     * 保存扩展信息（同一 catalogId + resourceType 唯一，存在则更新）
     *
     * @param dto 保存请求
     * @author hoxise
     * @since 2026/07/08
     */
    void saveExtra(MovieCatalogExtraSaveDTO dto);

    /**
     * 删除扩展信息
     *
     * @param id 主键 ID
     * @author hoxise
     * @since 2026/07/08
     */
    void deleteExtra(Long id);


    /**
     * 更新扩展信息（排序、显示名称、文件ID、云盘地址等）
     *
     * @param dto 更新请求
     * @author hoxise
     * @since 2026/07/08
     */
    void updateExtra(MovieCatalogExtraUpdateDTO dto);

    /**
     * 查询实际资源地址（校验密钥后返回预览地址或云盘地址）
     *
     * @param extraId 扩展信息 ID
     * @param secret  密钥（可为空，若记录设置了密钥则必填）
     * @return 资源地址（OSS 预览地址 或 云盘直链）
     * @author hoxise
     * @since 2026/07/09
     */
    String getResourceUrl(Long extraId, String secret);
}
