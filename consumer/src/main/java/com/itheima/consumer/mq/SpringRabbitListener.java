package com.itheima.consumer.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Slf4j
@Component
public class SpringRabbitListener {

    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String message) {
        log.info("监听到Simple.queue的消息:[{}]",message);
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


    @RabbitListener(queues = "direct.queue1")
    public void listenDirectQueue1(String message) throws InterruptedException {
        System.out.println("这里是directQueue1,接收到消息:" + message + ", "+ LocalTime.now());
        Thread.sleep(500);
    }

    @RabbitListener(queues = "direct.queue2")
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
}
