package com.xxl.user;

/**
 * @Description: 证件号码校验
 * 参考：https://blog.csdn.net/weixin_43938004/article/details/110240109
 * 正则：https://blog.csdn.net/qq_15437625/article/details/98601019
 * @Author: xxl
 * @Date: 2023/02/14 0:35
 * @Version: 1.0
 */
public class CardUtils {

    /**
     * 护照验证
     * 规则： G + 8位数字, P + 7位数字, S/D + 7或8位数字,等
     * 例： G12345678, P1234567
     */
    public static Boolean passportCard(String card) {
        String reg = "^([a-zA-z]|[0-9]){5,17}$";
        if (card.matches(reg) == false) {
            //护照号码不合格
            return false;
        } else {
            //校验通过
            return true;
        }
    }

    /**
     * 台湾居民来往大陆通行证
     * 规则： 新版8位或18位数字 或 旧版9位数字 + 英文字母 或 8位数字 + 英文字母
     * 样本： 12345678
     */
    public static Boolean isTWCard(String card) {
        String reg = "^\\d{8}|^[a-zA-Z0-9]{10}|^[a-zA-Z0-9]{9}|^\\d{18}$";
        if (card.matches(reg) == false) {
            //台湾居民来往大陆通行证号码不合格
            return false;
        } else {
            //校验通过
            return true;
        }
    }

    /**
     * 港澳居民来往内地通行证
     * 规则： H/M + 10位或6位数字
     * 例：H1234567890
     */
    public static Boolean isHKCard(String card) {
        String reg = "^([A-Z]\\d{6,10}(\\(\\w{1}\\))?)$";
        if (card.matches(reg) == false) {
            //港澳居民来往内地通行证号码不合格
            return false;
        } else {
            //校验通过
            return true;
        }
    }

    public static void main(String[] args) {
        System.out.println(passportCard("G12345678"));
        System.out.println(isTWCard("12345678"));
        System.out.println(isHKCard("H123456"));
    }

}

