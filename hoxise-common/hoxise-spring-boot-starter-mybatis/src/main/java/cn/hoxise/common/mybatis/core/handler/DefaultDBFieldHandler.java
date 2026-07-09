package cn.hoxise.common.mybatis.core.handler;

import cn.dev33.satoken.stp.StpUtil;
import cn.hoxise.common.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * mybatis通用参数自动填充
 *
 * @author hoxise
 * @since 2026/01/14 06:18:13
 */
public class DefaultDBFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //MybatisPlus用反射处理
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());

        // 填充创建人
        String userId = getLoginUserId();
        if (Objects.nonNull(userId)){
            this.strictInsertFill(metaObject, "creator", String.class, userId);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //填充更新时间
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        //如果登录用户不为空 则填充更新人
        String userId = getLoginUserId();
        if (Objects.nonNull(userId)){
            this.strictInsertFill(metaObject, "updater", String.class, userId);
        }
    }

    /**
     * 获取登录用户id
     *
     * @return java.lang.String
     */
    private String getLoginUserId() {
        Object userid = StpUtil.getLoginIdDefaultNull();
        return Objects.isNull(userid) ? null :String.valueOf(userid);
    }
}
