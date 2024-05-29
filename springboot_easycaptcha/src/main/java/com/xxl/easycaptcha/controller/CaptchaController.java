package com.xxl.easycaptcha.controller;

import com.pig4cloud.captcha.ArithmeticCaptcha;
import com.pig4cloud.captcha.SpecCaptcha;
import com.pig4cloud.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;

/**
 * @author xxl
 * @date 2024/5/29 23:57
 */
@Slf4j
@RestController
@RequestMapping("/aoi/captcha")
public class CaptchaController {

    @GetMapping("/getCode")
    public String getCode() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        log.info("生成的验证码结果为：{}，在项目中应该将结果保存到Redis中", verCode);

        // 将base64返回给前端
        return specCaptcha.toBase64();
    }

    @GetMapping("/getFontCode")
    public String getFontCode() throws IOException, FontFormatException {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        log.info("生成的验证码结果为：{}，在项目中应该将结果保存到Redis中", verCode);
        // 设置字体
        specCaptcha.setFont(Captcha.FONT_1);  // 有默认字体，可以不用设置
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        // 将base64返回给前端
        return specCaptcha.toBase64();
    }

    @GetMapping("/getMathCode")
    public String getMathCode() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        captcha.setLen(3);  // 几位数运算，默认是两位
        captcha.getArithmeticString();  // 获取运算的公式：3+2=?
        String code = captcha.text();// 获取运算的结果：5
        log.info("生成的验证码结果为：{}，在项目中应该将结果保存到Redis中", code);
        // 将base64返回给前端
        return captcha.toBase64();
    }
}