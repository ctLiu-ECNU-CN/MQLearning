package com.itheima.publisher;


import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public void testSimpleQueue(){
//        1 队列名
        String queueName = "simple.queue";


//        2 消息内容
        String message = "Hello";


//        3 发送消息
        rabbitTemplate.convertAndSend(queueName,"hello(from mq-demo)");
    }


    @Test
    public void testWorkQueue(){
//        1 队列名
        String queueName = "work.queue";
        for (int i = 0; i < 50; i++) {
            //        2 消息内容
            String message = "这是第" + i + "条消息";
            //        3 发送消息
            rabbitTemplate.convertAndSend(queueName,message);
        }

    }


    @Test
    public void testFanoutQueue(){
//        1 队列名
        String exchangeName = "hmall.fanout";
        for (int i = 1; i <= 5; i++) {
            //        2 消息内容
            String message = "Hello everyone, 这是第" + i + "条消息";
            //        3 发送消息到交换机
            rabbitTemplate.convertAndSend(exchangeName, null,  message);//routingKey先不管,三个参数重载使用发送到交换机的方法
        }

    }

}
