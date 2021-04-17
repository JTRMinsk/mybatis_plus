package org.salim.mybatis_plus;

import org.junit.jupiter.api.Test;
import org.salim.mybatis_plus.entity.User;
import org.salim.mybatis_plus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CRUDTest {

    @Autowired
    private UserMapper userMapper;

    @Test//这个类导入错误会导致启动失败
    public void test() {
        User user = new User();
        //user.setId(0l);
        // 建表时有已经定了ID是主键,不传id的时候会随便产生一个ID（id=1379733536392093698）
        //需要另外配置主键自增策略
        user.setAge(15);
        user.setName("Kivan");
        user.setEmail("jtrkiev@yahoo.com");

        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println(user);
    }

}
