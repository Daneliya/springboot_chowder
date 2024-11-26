package com.xxl.trimSpaces;


/**
 * 空字符串过滤测试
 *
 * @Author: xxl
 * @Date: 2024/11/26 13:17
 */
public class TrimSpacesTest {

    public static void main(String[] args) throws IllegalAccessException {
        TrimSpacesModel trimSpacesModel = new TrimSpacesModel();
        trimSpacesModel.setId("  ");
        trimSpacesModel.setName(" hello world ! ");

        TrimSpacesModel processedResult = TrimSpacesProcessor.process(trimSpacesModel);
        System.out.println("Processed Id: " + processedResult.getId());
        System.out.println("Processed Name: " + processedResult.getName());
        System.out.println(trimSpacesModel);
    }
}
