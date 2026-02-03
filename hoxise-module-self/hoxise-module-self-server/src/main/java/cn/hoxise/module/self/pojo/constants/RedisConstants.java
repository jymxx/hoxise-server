package cn.hoxise.module.self.pojo.constants;

/**
 * redis
 *
 * @author hoxise
 * @since 2026/2/3 下午3:52
 */
public class RedisConstants {


    /**
     * IP限流锁
     */
    public static final String RW_IP_LIMIT_KEY = "rw_trans_limit_ip::";

    /**
     * 开始翻译的锁
     */
    public static final String RW_START_TRANS_LIMIT_KEY = "rw_trans_start_limit_id::";

    /**
     *
     * 翻译表
     * 将redis看做数据库存储 这个就是是表名
     */
    public static final String RW_TABLE_TRANS_KEY = "rw_table_trans::";

    /**
     *
     * 子表
     */
    public static final String RW_SUB_TABLE_TRANS_KEY = "rw_sub_table_trans::";

    /**
     * 翻译结果状态
     */
    public static final String RW_TRANS_RESULT_LOCK_KEY = "rw_trans_result_lock::";
}
