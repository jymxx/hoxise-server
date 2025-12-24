package cn.hoxise.common.base.utils.convert;


import cn.hoxise.common.base.pojo.PageResult;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class ConvertUtil {

    /**
     * Mybatisplus的分页查询结果转到PageResult
     * @param	result	 查询结果
     * @param	voClass	 vo视图类
     * @return cn.hoxise.common.base.pojo.PageResult<S>
     * @date 2024/5/15 17:45
     * @author tangxin
     */
    public static <T,S> PageResult<S> convertPageResult(Page<T> result, Class<S> voClass){
        PageResult<S> pageResult = new PageResult<>();
        pageResult.setList(BeanUtil.copyToList(result.getRecords(), voClass));
        pageResult.setTotal(result.getTotal());
        return pageResult;
    }
}
