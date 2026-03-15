package cn.hoxise.module.movie.service;

import cn.hoxise.module.movie.controller.movie.dto.MovieScanUploadDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 影视管理
 *
 * @author hoxise
 * @since 2026/2/28 下午10:32
 */
public interface MovieManageService {


    /**
     * 前端扫描上传
     *
     * @param dto 影视目录扫描上传DTO
     * @author hoxise
     * @since 2026/03/06 20:57:39
     */
    void scanUpload(MovieScanUploadDTO dto);

    /**
     * 自动匹配逻辑
     *
     * @author hoxise
     * @since 2026/03/06 21:49:59
     */
    void autoMatch();

    /**
     * 简单匹配数据库 DB
     *
     * @param loginId 登录用户
     * @author hoxise
     * @since 2026/03/06 23:13:54
     */
    void matchDb(Long loginId);


//    /**
//     * 扫描目录
//     *
//     * @param scanUpdate 是否更新已存在的条目
//     * @author hoxise
//     * @since 2026/01/14 15:11:12
//     */
//    void dirScan(boolean scanUpdate);
//
//    /**
//     * 匹配bangumi数据
//     *
//     * @param isAllUpdate [isAllUpdate] 是否全量更新 false则只新增
//     * @author hoxise
//     * @since 2026/01/14 15:11:29
//     */
//    void allMatchingBangumi(boolean isAllUpdate);


}
