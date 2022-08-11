package com.xxl.san;

import com.xxl.san.entity.Author;

import java.util.Optional;

/**
 * @Author: xxl
 * @Date: 2022/08/11 10:47
 */
public class OptionalTest {

    public static void main(String[] args) {
        Author author = new Author();
        Optional<Author> authorOptional = Optional.ofNullable(author);
    }
}
