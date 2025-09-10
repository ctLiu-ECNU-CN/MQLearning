package com.itheima.publisher;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
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
            rabbitTemplate.convertAndSend(exchangeName, "",  message);//routingKey先不管,三个参数重载使用发送到交换机的方法
        }

    }


    @Test
    public void testDirectQueue(){
//        1 队列名
        String exchangeName = "hmall.direct";
        for (int i = 1; i <= 5; i++) {
            //        2 消息内容
            String message = "Hello everyone, 这是第" + i + "条消息";
            String message2 = "Yellow not blue";
            String message3 = "Blue not yellow";
            //        3 发送消息到交换机
            rabbitTemplate.convertAndSend(exchangeName, "red",  message);//routingKey先不管,三个参数重载使用发送到交换机的方法
            rabbitTemplate.convertAndSend(exchangeName, "yellow",  message2);//routingKey先不管,三个参数重载使用发送到交换机的方法
            rabbitTemplate.convertAndSend(exchangeName, "blue",  message3);//routingKey先不管,三个参数重载使用发送到交换机的方法
        }

    }


    @Test
    public void testTopicQueue(){
//        1 队列名
        String exchangeName = "hmall.topic";
        for (int i = 1; i <= 5; i++) {
            //        2 消息内容
            String message = "This is a news";
            String message2 = "cloudy";
            String message3 = "Blue not yellow";
            //        3 发送消息到交换机
            rabbitTemplate.convertAndSend(exchangeName, "china.news",  message);//routingKey先不管,三个参数重载使用发送到交换机的方法
            rabbitTemplate.convertAndSend(exchangeName, "china.weather",  message2);//routingKey先不管,三个参数重载使用发送到交换机的方法
            rabbitTemplate.convertAndSend(exchangeName, "blue",  message3);//routingKey先不管,三个参数重载使用发送到交换机的方法
        }

    }

    @Test
    public void testSendObject(){
        Map<String, Object> msg = new HashMap<>(2);
        msg.put("id",1);
        msg.put("content","hello");

        rabbitTemplate.convertAndSend("object.queue",msg);
    }



    @Test
    public void testConfirmCallback() throws InterruptedException {

//        创建correlationData
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        correlationData.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {

            @Override
            public void onFailure(Throwable ex) {
                log.error("spring amqp 处理结果异常",ex);
            }


            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                if(result.isAck()){
                    log.debug("收到确认消息,消息发送成功");
                }
                else{
                    log.error("收到确认消息,消息发送失败:{}",result.getReason());
                }
            }
        });

        rabbitTemplate.convertAndSend("hmall.direct", "bulu22", "hello", correlationData);
        Thread.sleep(3000);
    }

    /**
     * 带过期时间的消息
     */
    @Test
    void testDeadlExchange(){
        rabbitTemplate.convertAndSend("normal.direct", "hi", "hello", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("10000");
                return message;
            }
        });

    }

}
