package com.itheima.consumer.mq;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Map;

@Slf4j
@Component
public class SpringRabbitListener {

    private static final Logger log = LoggerFactory.getLogger(SpringRabbitListener.class);

    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(Message message) {
        log.info("消息ID:{}",message.getMessageProperties().getMessageId());

        log.info("监听到Simple.queue的消息:[{}]",new String(message.getBody()));
    }


//    两个消费者从同一个queue里获取数据测试
    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue(String message) throws InterruptedException {
        System.out.println("这里是消费者1,接收到消息:" + message + ", "+ LocalTime.now());
        Thread.sleep(200);
    }


    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue2(String message) throws InterruptedException {
        System.err.println("这里是消费者2,接收到消息:" + message + ", "+ LocalTime.now());
        Thread.sleep(25);
    }




    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String message) throws InterruptedException {
        System.out.println("这里是fanoutQueue1,接收到消息:" + message + ", "+ LocalTime.now());
        Thread.sleep(500);
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String message) throws InterruptedException {
        System.err.println("这里是fanoutQueue2,接收到消息:" + message + ", "+ LocalTime.now());
        Thread.sleep(500);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1",durable = "true"),
            exchange = @Exchange(name = "hmall.direct" , type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}
    ))
    public void listenDirectQueue1(String message) throws InterruptedException {
        System.out.println("这里是directQueue1,接收到消息:" + message + ", "+ LocalTime.now());
        Thread.sleep(500);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2",durable = "true"),
            exchange = @Exchange(name = "hmall.direct" , type = ExchangeTypes.DIRECT),
            key = {"red", "yellow"}
    ))
    public void listenDirectQueue2(String message) throws InterruptedException {
        System.err.println("这里是directQueue2,接收到消息:" + message + ", "+ LocalTime.now());
        Thread.sleep(500);
    }


    @RabbitListener(queues = "topic.queue1")
    public void listenTopicQueue1(String message) throws InterruptedException {
        System.err.println("这里是topicQueue1,接收到消息:" + message + ", "+ LocalTime.now());
        Thread.sleep(500);
    }

    @RabbitListener(queues = "topic.queue2")
    public void listenTopicQueue2(String message) throws InterruptedException {
        System.err.println("这里是topicQueue2,接收到消息:" + message + ", "+ LocalTime.now());
        Thread.sleep(500);
    }

    @RabbitListener(queues = "object.queue")
    public void listenObjectQueue(Map<String,Object> message) throws InterruptedException {
        System.out.println("Object.Queue:" + message + ", "+ LocalTime.now());
    }

    /**
     * 设置死信交换机
     * @param message 消息
     * @throws InterruptedException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "dlx.queue", durable = "true"),
            exchange = @Exchange(name = "dlx.direct", type = ExchangeTypes.DIRECT),
            key = {"hi"}
    ))
    public void listenDeadlQueue(String message) throws InterruptedException {
        log.info("消费者监听到dlx.queue的消息:{}",message);
    }

    /**
     * 普通exchange改为延迟交换机
     * @param message 消息
     * @throws InterruptedException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "delay.queue", durable = "true"),
            exchange = @Exchange(name = "delay.direct", type = ExchangeTypes.DIRECT, delayed = "true"),
            key = {"hi"}
    ))
    public void listenDelayedQueue(String message) throws InterruptedException {
        log.info("消费者监听到delay.queue的消息:{}",message);
    }





}
