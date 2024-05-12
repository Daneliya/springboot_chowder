package com.xxl.liteflow._0simple;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

/**
 * 转账消息提醒
 *
 * @author xxl
 * @date 2024/5/13 0:14
 */
@Component("userTransferNotify")
public class UserTransferNotifyComponent extends NodeComponent {

    @Override
    public void process() throws Exception {
        //转账消息提醒
        System.out.println("转账成功");
    }
}
