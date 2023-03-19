package com.xxl.vo;

import lombok.Data;

import javax.persistence.*;

/**
 * @Description: User类
 * @Author: xxl
 * @Date: 2023/02/13 10:43
 * @Version: 1.0
 */
@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @Column(name = "dbid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "uname")
    private String uname;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "age")
    private Integer age;

}