package com.xxl.controller;

import com.xxl.trimSpaces.TrimSpacesModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxl
 * @date 2022/5/8 18:57
 */
@RestController
public class DemoController2 {

    @GetMapping("/test")
//    public TrimSpacesModel test(@RequestParam(value = "111", required = false) String message) {
    public TrimSpacesModel test() {
        TrimSpacesModel trimSpacesModel = new TrimSpacesModel();
        trimSpacesModel.setId("  ");
        trimSpacesModel.setName(" hello world ! ");
        System.out.println(trimSpacesModel);
        return trimSpacesModel;
    }

}
