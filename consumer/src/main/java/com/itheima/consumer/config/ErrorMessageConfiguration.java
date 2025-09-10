package com.itheima.consumer.config;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ErrorMessageConfiguration {
    private final RabbitTemplate rabbitTemplate;

    @Bean
    public DirectExchange errorExchange() {
        return new DirectExchange("errorExchange");
    }

    @Bean
    public Queue errorQueue(){
        return new Queue("errorQueue");
    }

    @Bean
    public Binding errorQueueBinding(Queue errorQueue, DirectExchange errorExchange){
        return BindingBuilder.bind(errorQueue).to(errorExchange).with("error");
    }

    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate,"error.direct","error");
    }
}
