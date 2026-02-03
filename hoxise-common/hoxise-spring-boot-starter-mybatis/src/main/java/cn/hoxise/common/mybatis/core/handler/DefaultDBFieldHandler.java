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
        //如果是BaseDO的子类,直接赋值
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseDO baseDO) {

            LocalDateTime current = LocalDateTime.now();
            // 创建时间为空，则以当前时间为插入时间
            if (Objects.isNull(baseDO.getCreateTime())) {
                baseDO.setCreateTime(current);
            }
            // 更新时间为空，则以当前时间为更新时间
            if (Objects.isNull(baseDO.getUpdateTime())) {
                baseDO.setUpdateTime(current);
            }
            String userId = getLoginUserId();
            // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
            if (Objects.nonNull(userId) && Objects.isNull(baseDO.getCreator())) {
                baseDO.setCreator(userId);
            }
            // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
            if (Objects.nonNull(userId) && Objects.isNull(baseDO.getUpdater())) {
                baseDO.setUpdater(userId);
            }

        }else if (Objects.nonNull(metaObject)){
            //MybatisPlus自行用反射处理
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());

            String userId = getLoginUserId();
            if (Objects.nonNull(userId)){
                this.strictInsertFill(metaObject, "creator", String.class, userId);
            }
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
