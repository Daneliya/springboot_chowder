package com.tcmp.common.rabbitmq.delay;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 启动类
 */
@SpringBootApplication
public class SpringbootRabbitmqDelayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRabbitmqDelayApplication.class, args);
    }

    /**
     * JSON转换器
     *
     * @return
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
