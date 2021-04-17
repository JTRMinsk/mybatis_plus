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
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateDt", new Date(), metaObject);
    }
}
