package org.salim.mybatis_plus.entity;

import lombok.Data;

@Data// lombok 可以生成对应类的get/set/toString/构造函数
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
