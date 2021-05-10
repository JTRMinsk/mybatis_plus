package org.salim.mybatis_plus.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement//开启事务管理，这个作用在哪里？
@Configuration
//@MapperScan("org.salim.mybatis_plus.mapper")//这个可以从Application类转移过来，通过Configuration注解
public class MybatisPlusConfig {
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
        //MyBatis中的乐观锁插件，3.4版本的mybatis plus听说就不用这个bean了
    }
}
