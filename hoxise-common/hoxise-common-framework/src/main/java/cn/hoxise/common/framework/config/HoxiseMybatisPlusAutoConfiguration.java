package cn.hoxise.common.framework.config;

import cn.hoxise.common.framework.core.mybatis.DefaultDBFieldHandler;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;


/**
 *  mybatis plus 配置类
 *
 * @author hoxise
 * @since 2026/01/14 06:15:38
 */
@AutoConfiguration
public class HoxiseMybatisPlusAutoConfiguration {

    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
      MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
      interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // 如果配置多个插件, 切记分页最后添加
      // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
      return interceptor;
    }

    /**
     * 添加字段填充
     */
    @Bean
    public DefaultDBFieldHandler defaultDbFieldHandler() {
        return new DefaultDBFieldHandler();
    }


}
