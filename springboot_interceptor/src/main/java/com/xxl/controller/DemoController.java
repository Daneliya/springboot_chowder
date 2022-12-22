package com.xxl.controller;

import com.xxl.request.QueryRequest;
import com.xxl.util.UserUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxl
 * @date 2022/12/22 22:05
 */
@RestController
public class DemoController {

    @RequestMapping("/test")
    public String test(@RequestBody QueryRequest queryRequest) {
        System.out.println(UserUtils.getLoginUser());
        System.out.println(queryRequest.getHospitalId());
        System.out.println(queryRequest.getId());
        return "hello";
    }

}
