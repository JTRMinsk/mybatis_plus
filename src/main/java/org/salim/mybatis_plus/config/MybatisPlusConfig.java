package org.salim.mybatis_plus.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement//开启事务管理，这个作用在哪里？
@Configuration
//@MapperScan("org.salim.mybatis_plus.mapper")//这个可以从Application类转移过来，通过Configuration注解
public class MybatisPlusConfig {
    //TODO: Interceptor 在mybatis plus里面 是什么东西？
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
        //MyBatis PLUS中的乐观锁插件，3.4版本的mybatis plus听说就不用这个bean了
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
        //MyBatis PLUS中的分页插件
    }

//    @Bean
//    public ISqlInjector sqlInjector() {
//        return new LogicSqlInjector();
//    }//3.x版本的mybatis plus不需要这个插件了，现在是3.3.1

//    @Bean
//    @Profile({"dev", "test"})
//    public PerformanceInterceptor performanceInterceptor () {
//        //3.2.0之后的版本就没有这项了，要用第三方工具
//    }
}
