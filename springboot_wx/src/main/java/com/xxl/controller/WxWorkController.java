package com.xxl.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxl.aes.WXBizMsgCrypt;
import com.xxl.constants.QywechatCallback;
import com.xxl.constants.QywechatUserBean;
import com.xxl.util.MessageUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/05/18 15:02
 * @Version: 1.0
 */
@RestController
public class WxWorkController {

    private static final Logger logger = LoggerFactory.getLogger(WxWorkController.class);

    private static final String token = "f7RjL6fJgIaX8Fi";
    private static final String encodingAESKey = "zcEBfSf7BFLMMHut0HlM6dh8HLNltaPNju9vK0Cn8FV";

    /**
     * get 请求  验签.
     *
     * @param msgSignature 加密
     * @param timestamp    时间戳
     * @param nonce        随机
     * @param echostr
     * @param response
     * @throws Exception
     */
    @GetMapping(value = "/callback/interAspect")
    public void reveiceMsg(@RequestParam(name = "msg_signature") final String msgSignature,
                           @RequestParam(name = "timestamp") final String timestamp,
                           @RequestParam(name = "nonce") final String nonce,
                           @RequestParam(name = "echostr") final String echostr,
                           final HttpServletResponse response) throws Exception {
        //企业回调的url-----该url不做任何的业务逻辑，仅仅微信查看是否可以调通.
//        QywechatCallback qywechatCallback = QywechatCallback.TEST;
////        logger.info("=========\n"+" msg_signature {}, timestamp {}, nonce {} , echostr {} \n", msgSignature, timestamp, nonce, echostr,"=========\n");
//        logger.info("\n==================" + "msg_signature {}, timestamp {}, nonce {} , echostr {} ", msgSignature, timestamp, nonce, echostr, "==================\n");
//        WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAESKey, qywechatCallback);
//        // 随机字符串
//        String sEchoStr = wxcpt.VerifyURL(msgSignature, timestamp, nonce, echostr);
//        PrintWriter out = response.getWriter();
//        try {
//            //必须要返回解密之后的明文
//            if (StringUtils.isBlank(sEchoStr)) {
//                logger.info("=========\n" + "URL验证失败" + "=========\n");
//            } else {
//                logger.info("=========\n" + "验证成功!" + "=========\n");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("", e);
//        }
//        out.write(sEchoStr);
//        out.flush();
    }

    /**
     * 企业微信客户联系回调
     *
     * @param request       request
     * @param sMsgSignature 签名
     * @param sTimestamp    时间戳
     * @param sNonce        随机值
     * @return success
     */
    @ResponseBody
    @PostMapping(value = "/callback/interAspect")
    public String acceptMessage(final HttpServletRequest request,
                                @RequestParam(name = "msg_signature") final String sMsgSignature,
                                @RequestParam(name = "timestamp") final String sTimestamp,
                                @RequestParam(name = "nonce") final String sNonce) {

//        JSONObject result = new JSONObject();
//        result.put("status", "0");
//        result.put("exception", new Exception());
//        result.put("message", "未处理！");
//        result.put("dealStatus", 0);
//        String logId = null;
//
//        QywechatCallback qywechatCallback = QywechatCallback.TEST;
//        try {
//            logger.info("\n==================" + "msg_signature {}, timestamp {}, nonce {} ", sMsgSignature, sTimestamp, sNonce, "==================\n");
//            InputStream inputStream = request.getInputStream();
//            String sPostData = IOUtils.toString(inputStream, "UTF-8");
//            QywechatUserBean qywechatUserBean = new QywechatUserBean();
//            qywechatUserBean.setMsgSignature(sMsgSignature);
//            qywechatUserBean.setNonce(sNonce);
//            qywechatUserBean.setQywechatCallback(qywechatCallback);
//            qywechatUserBean.setTimestamp(sTimestamp);
//            qywechatUserBean.setsPostData(sPostData);
//            logger.info("\n==================sPostData==================\n" + sPostData);
//
//
//            //下面这些操作都可以异步  如果处理请求比较慢，企业微信会重复推送 这里需要去重保证执行唯一
//            WXBizMsgCrypt msgCrypt = new WXBizMsgCrypt(qywechatUserBean.getQywechatCallback());
//            //解密
//            String sMsg = msgCrypt.decryptMsg(qywechatUserBean);
//            //将post数据转换为map
//            Map<String, String> dataMap = MessageUtil.parseXml(sMsg);
//            logger.info("\n==================dataMap==================\n" + dataMap.toString());
//
//            //处理自己的业务逻辑
//            //systemService.getEncryptRespMessage(dataMap,result);
//        } catch (Exception e) {
//            result.put("status", "1");
//            result.put("message", "接口处理失败!");
//            result.put("exception", e);
//            result.put("dealStatus", 2);
//            logger.error("接口调用内部处理异常：", e);
//        } finally {
////            更新微信操作
//            //systemService.wechatExecResponse(logId, result);
//        }
        return "success";
    }

}
