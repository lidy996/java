package com.example.demo.list;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RestController
@RequestMapping("/list")
public class ListController {

    @Autowired
    @Qualifier("newFixedThreadPool")
    private ExecutorService newFixedThreadPool;

    /**
     * 验证 ArrayList、 LinkedList、Stack、Vector多线程安全性问题
     *
     * @throws InterruptedException
     */
    @RequestMapping("/test")
    public void list() throws InterruptedException {
        for (int j = 0;j < 10;j++){
            int count = 100000;
            CountDownLatch countDownLatch = new CountDownLatch(count);
            List<Object> arrayList = new ArrayList<>(count);
            List<Object> syncArrayList = new ArrayList<>(count);
            List<Object> synchronizedArrayList = Collections.synchronizedList(new ArrayList<>(count));
            List<Object> linkedList = new LinkedList<>();
            List<Object> stack = new Stack<>();
            List<Object> vector = new Vector<>();
            AtomicInteger integer = new AtomicInteger(0);
            for (int index = 1;index <= count;index++) {
                newFixedThreadPool.execute(() -> {

                    // safely
                    integer.addAndGet(1);

                    // no safely
                    arrayList.add(1);

                    // safely
                    this.add(syncArrayList);
                    synchronizedArrayList.add(1);

                    // no safely
                    linkedList.add(1);

                    // safely
                    stack.add(1);
                    vector.add(1);

                    countDownLatch.countDown();
                });
            }
            countDownLatch.await();
            System.out.println(integer);
            log.info("-----------------------");
            log.info("arrayList:{}",arrayList.size());
            log.info("syncArrayList:{}",syncArrayList.size());
            log.info("synchronizedArrayList:{}",synchronizedArrayList.size());
            log.info("linkedList:{}",linkedList.size());
            log.info("stack:{}",stack.size());
            log.info("vector:{}",vector.size());
            log.info("-----------------------");
        }
    }

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        for(int i = 1;i < 4;i++){
            Iterator<Object> iterator = list.iterator();
            while (iterator.hasNext()){
                Object o = iterator.next();
                if (i == 1 && o.equals(3)){
                    iterator.remove();
                }
                if (i == 2 && o.equals(5)){
                    iterator.remove();
                }
                if (i == 3 && o.equals(1)){
                    iterator.remove();
                }
            }
            System.out.println(list.toString());
        }


//        //普通for循环遍历删除
//        for (int i = 0; i < list.size(); i++) {
//            Object o = list.get(i);
//            if (o.equals(2)) {
//                list.remove(i);
//                //★★★★★ 角标减一
//                i--;
//            }
//        }
//        System.out.println(list.toString());
    }

    public synchronized void add(List<Object> syncArrayList){
        syncArrayList.add(1);
    }
}
