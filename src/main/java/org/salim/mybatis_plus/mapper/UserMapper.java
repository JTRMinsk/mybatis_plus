package org.salim.mybatis_plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;//来自mybatis plus
import org.salim.mybatis_plus.entity.User;
import org.springframework.stereotype.Repository;

@Repository//这里其实也可以不加，报不报错主要看IDEA版本， 这里的mapper对象注入是要考mybatis和@MapperScan注解来实现的
public interface UserMapper extends BaseMapper<User> {//类后面加范型的意义是什么？类似一个输入参数？
    //这样的定义方式说明这个UserMapper用来承载User的单条数据
}
