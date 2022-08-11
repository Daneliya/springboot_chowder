package com.xxl.sangeng;

import com.xxl.sangeng.entity.Author;

import java.util.Optional;

/**
 * @Author: xxl
 * @Date: 2022/08/11 10:47
 */
public class OptionalDemo {

    public static void main(String[] args) {
        Author author = new Author();
        Optional<Author> authorOptional = Optional.ofNullable(author);
    }
}
