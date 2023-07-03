package com.xxl.repeat_submit.controller;

import com.xxl.repeat_submit.annotation.SubmitLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxl
 * @date 2022/11/17 23:44
 */
@RestController
@RequestMapping("/order")
public class OrderController {

//    @Autowired
//    private OrderService orderService;
//
//    /**
//     * 下单
//     * @param request
//     * @return
//     */
//    @SubmitToken
//    @PostMapping(value = "/confirm")
//    public ResResult confirm(@RequestBody OrderConfirmRequest request){
//        //调用订单下单相关逻辑
//        orderService.confirm(request);
//        return ResResult.getSuccess();
//    }


    @SubmitLimit(customerHeaders = {"appId", "token"}, customerTipMsg = "请勿重复提交！")
    @GetMapping(value = "/confirm")
    public String confirm(@RequestParam(value = "userName", required = false) String userName) {
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return ResResult.getSuccess();
        return userName;
    }
}