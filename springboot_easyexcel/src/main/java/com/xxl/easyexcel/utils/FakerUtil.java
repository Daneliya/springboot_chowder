package com.xxl.easyexcel.utils;

import com.github.javafaker.Faker;
import com.xxl.easyexcel.entity.ExportExcelEntity;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 生成测试数据
 *
 * @author xxl
 * @date 2024/5/28 0:11
 */
public class FakerUtil {

    /**
     * faker 指定汉语，默认英语
     */
    private static Faker FAKER = new Faker(Locale.CHINA);

    /**
     * 随机生成一定数量学生
     *
     * @param number 数量
     * @return 学生
     */
    public static List<ExportExcelEntity> generateStudentList(final int number) {
        return Stream.generate(() -> new ExportExcelEntity(
                        FAKER.name().fullName(),
                        FAKER.phoneNumber().cellPhone(),
                        FAKER.address().city() + FAKER.address().streetAddress(),
                        FAKER.idNumber().validSvSeSsn()))
                .limit(number)
                .collect(Collectors.toList());
    }

}
