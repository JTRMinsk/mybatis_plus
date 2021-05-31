package org.salim.mybatis_plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.salim.mybatis_plus.entity.User;
import org.salim.mybatis_plus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

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
        user.setAge(22);
        user.setName("wrapper test 2");
        user.setEmail("ABC@qq.com");

        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println(user);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(12l);
        user.setDeleted(0);

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

    @Test
    public void testPagedSelect () {
        //分页的意义是什么？分页实际上就是这么用的吗？是为了应对什么使用情况？
        Page<User> page = new Page<>(1, 3);//在数据库中定点、定长的结果集（第一条开始的前三条）
        userMapper.selectPage(page, null);//将这个定位和长度描述作为query条件发至数据库 Query Wrapper是什么？
        //SELECT id,name,age,email,create_time,update_time FROM user LIMIT 0,3
        //LIMIT 0,3
        //userMapper.selectPage(page, null);  如果直接运行，会把结果集放到page对象中
        page.getRecords().forEach(System.out::println);

    }

    @Test
    public void testMappedPagedSelect () {
        Page<Map<String, Object>> page = new Page<>(1, 5);
        //得到不同类型的结果集，这个是Map，上面这个是User
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page, null);
        mapIPage.getRecords().forEach(System.out::println);
    }

    //物理删除与逻辑删除
    @Test
    public void testDeleteById () {
        int result = userMapper.deleteById(1l);
        System.out.println(result);
    }

    @Test
    public void testBatchDelete () {
        int result = userMapper.deleteBatchIds(Arrays.asList(2,3l));//为什么Long和Interger都可以?
    }


    @Test
    public void testDeleteByMap () {//条件删除,如何使用与/或条件？
        Map<String, Object> map = new HashMap<>();
        //map.put("name", "sandy");//不区分大小写
        //map.put("email", "jtrkiev@yahoo.com");//不同column的条件是
        //map.put("name", "Billie");//因为是Map，所以不能输入相同key
        //map.put();//复杂条件找不到内容？

        map.put("name", "logic delete test3");//只要满足条件，可以删除多条

        int result = userMapper.deleteByMap(map);//这里的Map指的就是Map类
    }

    @Test
    public void testLogicDelete () {
        //需要添加User字段，加注解@TableLogic标示逻辑删除的字段
        //需要在handle里面给insert的内容添加初始值
        //需要配置delete flag的赋值规则（目前是用0/1作为默认值，所以不用加）：
            //mybatis-plus.global-config.db-config.logic-delete-value=1
            //mybatis-plus.global-config.db-config.logic-not-delete-value=0
        //3.x以前版本的mybatis Plus需要在Config里面增加Bean：LogicSqlInjector()来开启功能
        //使用正常的删除逻辑操作即可
        //delete字段为NULL的也不会被

        //一旦Mark删除，不能通过update来重置回来，也不能被search到
    }

    @Test
    public void testQueryWrapper () {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //queryWrapper这里的是一个自定义的条件
        queryWrapper
                .isNotNull("version")
                //.isNull("")
                .eq("email","ABC@qq.com")//等于
                .ge("age", 23);//大于等于
        //EQ： EQUAL 等于
        //NE： NOT EQUAL 不等于
        //GT： GREATER THAN 大于　
        //LT ： LESS THAN 小于
        //GE： GREATER THAN OR EQUAL 大于等于
        //LE： LESS THAN OR EQUAL 小于等于

        List<Map<String, Object>> usersA = userMapper.selectMaps(queryWrapper);
        List<User> usersB = userMapper.selectList(queryWrapper);

        System.out.println(usersA);
        System.out.println(usersB);
    }

    @Test
    public void testUpdateWrapper () {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();//这里的Wrapper用得也不一样
    }

}
