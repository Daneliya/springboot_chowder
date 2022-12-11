package com.xxl.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体
 *
 * @author xxl
 * @date 2022/3/1 0:03
 */
@Data
public class User implements Serializable {

    private int id;
    private String name;
    private int age;
    private double score;

}