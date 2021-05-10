package org.salim.mybatis_plus;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.salim.mybatis_plus.entity.User;
import org.salim.mybatis_plus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
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
        user.setAge(33);
        user.setName("version test3");
        user.setEmail("1021264431@qq.com");

        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println(user);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(11l);
        user.setAge(40);

        int result = userMapper.updateById(user);//为什么没有updateByXXX, id由@TableId(type = IdType.AUTO)
        System.out.println(result);
    }

    @Test
    public void testOptiUpdate () {
        User user = userMapper.selectById(11l);

        user.setEmail("Optest2@test.com");

        int result = userMapper.updateById(user);
        //当程序先查询、再修改的时候，就会更新version字段，
        // 而执行testUpdate的时候不先query，就不会更新version字段，为什么？
        //与query与否无关，只与传不传参数有关，传了就会进行判断，不传就直接update
    }

    @Test
    public void testOptiUpdateConflict () {
        User userA = userMapper.selectById(11l);
        User userB = userMapper.selectById(11l);

        userA.setEmail("OptestA@test.com");
        userB.setEmail("OptestB@test.com");

        int resultA = userMapper.updateById(userA);
        int resultB = userMapper.updateById(userB);

        System.out.println(resultA);
        System.out.println(resultB);
        //最后结果：只改了A，没有改B
    }

    @Test
    public void testOptiUpdateNoPreCheck () {
        User user = new User();
        user.setId(11l);
        user.setAge(40);
        user.setVersion(6);//这个数必须是当前数据的version值，不多不少，否则无法进行下去

        int result = userMapper.updateById(user);//为什么没有updateByXXX, id由@TableId(type = IdType.AUTO)
        System.out.println(result);
    }

    @Test
    public void testSelectByBatch () {
        List<Long> ids = new ArrayList<>();
        ids.add(1l);
        ids.add(2l);
        List<User> users = userMapper.selectBatchIds(ids);//ids也可以直接用Arrays.asList(1, 2, 3)替换

        System.out.println(users);
    }

}
