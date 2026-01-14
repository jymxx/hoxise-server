package cn.hoxise.common.base.utils.convert;


import cn.hoxise.common.base.pojo.PageResult;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * ConvertUtil 转换工具
 *
 * @author hoxise
 * @since 2026/01/14 06:51:33
 */
public class ConvertUtil {

    /**
     * MybatisPlus的分页查询结果转到PageResult 通常是VO
     *
     * @param result  查询结果
     * @param voClass vo视图类
     * @return cn.hoxise.common.base.pojo.PageResult<S>
     * @author hoxise
     * @since 2026/01/14 06:51:53
     */
    public static <T,S> PageResult<S> convertPageResult(Page<T> result, Class<S> voClass){
        PageResult<S> pageResult = new PageResult<>();
        pageResult.setList(BeanUtil.copyToList(result.getRecords(), voClass));
        pageResult.setTotal(result.getTotal());
        return pageResult;
    }
}
