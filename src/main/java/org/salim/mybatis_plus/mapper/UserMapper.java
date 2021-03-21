package org.salim.mybatis_plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;//来自mybatis plus
import org.salim.mybatis_plus.entity.User;

public interface UserMapper extends BaseMapper<User> {
    //这样的定义方式说明这个UserMapper用来承载User的单条数据
}
