package com.example.demo.kafka;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

@Slf4j
@Api(tags = "kafka")
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    @Qualifier("newFixedThreadPool")
    private ExecutorService newFixedThreadPool;

    /**
     * kafka生产者发送消息
     * 多个partition
     * 多个消费者
     *
     * 验证：
     *  1、消息丢失 生产丢失、broker丢失、消费丢失
     *  2、刷盘机制、同步机制
     */
    @GetMapping("/send")
    @ApiOperation(value = "发送消息")
    public void send() throws InterruptedException {
        Long time_01 = System.currentTimeMillis();
        int messageCount = 1;
        CountDownLatch downLatch = new CountDownLatch(messageCount);
        for (int i = 0;i < messageCount;i++){
            Thread.sleep(100);
            String key = UUID.randomUUID().toString();
            String data = "message_" + i;
            // 线程池发送
//            newFixedThreadPool.execute(()->{
//                downLatch.countDown();
//                kafkaTemplate.send("test_01",key,data);
//            });
            // 发送回调
            try {
                ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("test_01" ,key ,data);
                future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                    @Override
                    public void onSuccess(SendResult<String, Object> result) {
                        log.info("msg send success:{}",result.toString());
                    }
                    @Override
                    public void onFailure(Throwable e) {
                        log.info("msg send failed:{}",e.toString());
                    }
                });
            } catch (Exception e){
                log.info("msg send exception:{}",e.toString());
            }
        }
//        downLatch.await();
        Long time_02 = System.currentTimeMillis();
        log.info("发送消息耗时:{}",time_02 - time_01);
    }
}
