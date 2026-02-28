package com.xxl.elasticsearch.controller;

import com.xxl.elasticsearch.pojo.PageResult;
import com.xxl.elasticsearch.pojo.RequestParams;
import com.xxl.elasticsearch.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 酒店 控制层
 *
 * @author xxl
 * @date 2026/2/24 11:33
 */
@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    @PostMapping("list")
    public PageResult search(@RequestBody RequestParams params) {
        return hotelService.search(params);
    }

    @PostMapping("filters")
    public Map<String, List<String>> getFilters(@RequestBody RequestParams params) {
        return hotelService.getFilters(params);
    }

    @GetMapping("suggestion")
    public List<String> getSuggestion(@RequestParam("key") String key) {
        return hotelService.getSuggestion(key);
    }
}
