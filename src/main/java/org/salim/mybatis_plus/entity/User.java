package org.salim.mybatis_plus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data// lombok 可以生成对应类的get/set/toString/构造函数
public class User {
    @TableId(type = IdType.AUTO)//AUTO是主键自增
    // 默认是@TableId(type = IdType.ASSIGN_ID)主键策略是雪花算法（不连贯的分布式ID）
    //想要默认AUTO，需要配置全局变量：mybatis-plus.global-config.db-config.id-type=auto
    private Long id;// 主键自增配置，在这里指出id是这个table的主键

    private String name;
    private Integer age;
    private String email;

    @TableField(fill = FieldFill.INSERT)
    //自动填充功能，可以交给数据库自己设置时间默认值，但更加推荐用java来做，达成对不同种类数据库的通用性
    //并且要实现元对象处理接口MetaObjectHandler
    private Date createDt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDt;

    @Version//一旦加了这个注解，所有的增删改查就必须要带上这个值
    @TableField(fill = FieldFill.INSERT)//为什么这里是INSERT 而不是 UPDATE？
    private Integer version;
    //通过insert或者update的时候输入上次的版本号，来验证数据是否已经改动，达成乐观锁, update 的时候再加一个where version = ?可以完成
    //使用这样的乐观锁，总要拿到当前数据，是否增加了工作量？
    //乐观锁（保存结果前先检查数据是否改动，如果改动，重新操作），悲观锁（一方操作的时候完全锁死数据，不让其他人操作）

}
