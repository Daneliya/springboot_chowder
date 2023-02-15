package com.xxl.controller;

import com.xxl.service.PeopleService;
import com.xxl.pojo.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/02/15 23:34
 * @Version: 1.0
 */
@RestController
@RequestMapping("people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }


    @GetMapping("/list")
    public List<People> getAllPeople() {
        return peopleService.list();
    }

    @GetMapping("/insert")
    public String addPeople() {
        peopleService.save(new People("xxl"));
        return "添加成功";
    }

}