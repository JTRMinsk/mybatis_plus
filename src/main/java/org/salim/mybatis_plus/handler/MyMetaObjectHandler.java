package org.salim.mybatis_plus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    //metaObject 就是要被填充的对象，这里是User。但如何map到User？
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createDt", new Date(), metaObject);
        this.setFieldValByName("updateDt", new Date(), metaObject);
        this.setFieldValByName("version", 1, metaObject);
        //为了实现乐观锁，需要添加一个填充version默认值的代码, 这个version可以是查出来的，也可以是自己加的，但是乐观锁的原理是要先数据库差version
        this.setFieldValByName("deleted", 0, metaObject);//默认首次insert的时候先
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateDt", new Date(), metaObject);
        //为什么version的handle不是在这里？（不需要，拿来的是几，传回去的也是几）
    }
}
