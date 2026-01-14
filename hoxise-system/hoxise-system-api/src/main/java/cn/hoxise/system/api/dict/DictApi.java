package cn.hoxise.system.api.dict;

/**
 * 暴露给其它模块的字典接口
 *
 * @author hoxise
 * @since 2026/01/14 06:11:24
 */
public interface DictApi {

    /**
     * getByKey 获取字典
     *
     * @param key 字典key
     * @return value
     * @author hoxise
     * @since 2026/01/14 06:13:34
     */
    String getByKey(String key);
}
