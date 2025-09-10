package com.itheima.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NormalConfiguration {

    @Bean
    public DirectExchange normalExchange() {
        return new DirectExchange("normal.direct");
    }

    @Bean
    public Queue normalQueue() {
        return QueueBuilder.durable("normal.queue").deadLetterExchange("dlx.direct").build();
    }

    @Bean
    public Binding normalExchangeBinding(Queue normalQueue, DirectExchange normalExchange){
        return BindingBuilder.bind(normalQueue).to(normalExchange).with("hi");
    }

}
