package com.xxl.springboot.init.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xxl
 * @date 2025/1/2 0:32
 */
@Component
public class MySchedulerTask {
    @Scheduled(fixedRate = 2000)                // 采用间隔调度，每2秒执行一次
    public void runJobA() {                    // 定义一个要执行的任务
        System.out.println("【*** MyTaskA - 间隔调度 ***】"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .format(new Date()));
    }

    @Scheduled(cron = "* * * * * ?")            // 每秒调用一次
    public void runJobB() {
        System.err.println("【*** MyTaskB - 间隔调度 ***】"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .format(new Date()));
    }
}
