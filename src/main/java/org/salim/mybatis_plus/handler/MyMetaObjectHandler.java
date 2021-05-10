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
        //为了实现乐观锁，需要添加一个填充version默认值的代码（猜测，即使这里的值不加，也会自增）
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateDt", new Date(), metaObject);
        //为什么这里不用加Handler？
    }
}
