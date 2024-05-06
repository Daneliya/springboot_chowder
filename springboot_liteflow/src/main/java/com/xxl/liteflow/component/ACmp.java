package com.xxl.liteflow.component;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

/**
 * a组件
 *
 * @author xxl
 * @date 2024/5/6 22:50
 */
@Component("a")
public class ACmp extends NodeComponent {

    @Override
    public void process() {
        //do your business
        System.out.println("a executed!");
    }
}
