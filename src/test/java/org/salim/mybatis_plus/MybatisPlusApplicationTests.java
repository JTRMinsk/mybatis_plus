package org.salim.mybatis_plus;

import org.junit.jupiter.api.Test;
import org.salim.mybatis_plus.entity.User;
import org.salim.mybatis_plus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void test() {
        User user = new User();
        //user.setId(0l);
        // 建表时有已经定了ID是主键,不传id的时候会随便产生一个ID（id=1379733536392093698）不连贯的分布式ID，雪花算法
        //需要在entity中另外配置主键自增；数据库建表的时候也要配置,并且重新设置自增的基数
        user.setAge(20);
        user.setName("version test");
        user.setEmail("1021264431@qq.com");

        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println(user);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(1l);
        user.setAge(30);

        int result = userMapper.updateById(user);//为什么没有updateByXXX, id由@TableId(type = IdType.AUTO)
        System.out.println(result);
    }

    public void testConcurrent () {

    }

}
