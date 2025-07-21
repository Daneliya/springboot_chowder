package com.xxl.springboot.rabbitmq.tcmp;

import com.xxl.springboot.rabbitmq.fanout.FanoutConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xxl
 * @Date: 2025/6/27 16:32
 */
@Configuration
public class TcmpConfig {

    /**
     * 声明交换机
     *
     * @return Fanout类型交换机
     */
    @Bean
    public FanoutExchange leaveExchange() {
        return new FanoutExchange("leave.fanout.exchange");
    }

    /**
     * 第1个队列，带消息超时时间（例如：30分钟）
     * 【QueueBuilder】使用 QueueBuilder 来创建带有 TTL 的队列，而不是直接使用 new Queue()
     * 【关于 x-message-ttl】单位是 毫秒，设置后，消息会在指定时间内未被消费则自动过期，如果队列中堆积了很多消息，可以避免占用过多内存/磁盘空间
     * 【关于x-max-priority】可选：优先级支持（非必须）
     * 【关于 durable】使用 .durable(true) 表示队列持久化（重启后不丢失）；默认就是 durable，但建议显式声明，增强可读性
     */
    @Bean
    public Queue leaveQueue1() {
        return QueueBuilder.durable("demo1.leave.queue")
                .withArgument("x-message-ttl", FanoutConstants.queue1_expire_mills) // 30分钟 = 1800000 ms
                .build();
    }

    /**
     * 绑定队列和交换机
     */
    @Bean
    public Binding bindingQueue1111(Queue leaveQueue1, FanoutExchange leaveExchange) {
        return BindingBuilder.bind(leaveQueue1).to(leaveExchange);
    }

}
