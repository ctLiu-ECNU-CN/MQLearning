package com.itheima.consumer.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfiguration {

    @Bean
    public FanoutExchange fanoutExchange() {
//        return new FanoutExchange("hmall.fanout1");

        FanoutExchange exchange = ExchangeBuilder.fanoutExchange("hmall.fanout1").build();
        return exchange;
    }

    @Bean
    public Queue fanoutQueue1() {

//        return new Queue("hmall.queue1");
        Queue queue = QueueBuilder.durable("fanout.queue1").build(); //durable 持久化到磁盘
        return queue;
    }

    @Bean
    public Queue fanoutQueue2() {

//        return new Queue("hmall.queue2");
        Queue queue = QueueBuilder.durable("fanout.queue2").build(); //durable 持久化到磁盘
        return queue;
    }


    /**
     * 将队列绑定到交换机上
     * @param fanoutQueue1
     * @param fanoutExchange
     * @return
     */
    @Bean
    public Binding fanoutQueue1Binding(Queue fanoutQueue1, FanoutExchange fanoutExchange) {// 这里自动注入Bean (这里的fanoutQueue1就是上面代码里的)
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    @Bean
    public Binding fanoutQueue2Binding(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }
}
