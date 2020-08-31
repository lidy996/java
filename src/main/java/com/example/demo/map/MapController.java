package com.example.demo.map;

import com.example.demo.set.SetController;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

@Slf4j
@Controller
@RequestMapping("/map")
public class MapController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    @Qualifier("newFixedThreadPool")
    private ExecutorService newFixedThreadPool;

    public static void main(String[] args) {
        String test = "1";
        System.out.println(test.hashCode());

        // 扩容
        Map<Object,Object> capMap = new HashMap<>();
        for (int i = 0;i < 16;i++){
            if (i == 11){
                log.info("开始扩容");
            }
            capMap.put(i,i);
            log.info("i:{}",i);
        }

        Map map = new HashMap();
        Map testMap = Collections.synchronizedMap(map);
        testMap.put(null,null);


        Map tableMap = new Hashtable();
        tableMap.put(1,1);

        Map safeMap = new ConcurrentHashMap();
        safeMap.put(1,1);

//        Set<Object> keySet = capMap.keySet();
//        keySet.forEach(key->{
//            System.out.println(key);
//            capMap.remove(key);
//        });


        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(1,1);

        /**
         * 验证TreeMap顺序
         */
        /*
            0
            1
            2
            3
            4
            5
            6
            7
            8
            9
            32
            56
            124
         */
        TreeMap treeMap = new TreeMap();
        treeMap.put(124,124);
        treeMap.put(32,32);
        treeMap.put(56,56);
        treeMap.put(3,3);

        for (int i = 0;i < 10;i++){
            treeMap.put(i,i);
        }

        Set stringSet = treeMap.keySet();
        for (Object key : stringSet){
            System.out.println(treeMap.get(key));
        }
    }

    @RequestMapping("hashmap/put")
    public void putHashMap(){
        HashMap<Object,Object> hashMap = new HashMap<>();
        Long time_01 = System.currentTimeMillis();
        int messageCount = 1000;
        for (int i = 0;i < messageCount;i++){
            int index = i;
            newFixedThreadPool.execute(() -> hashMap.put(index,index));
//            hashMap.put(index,index);
        }
        Long time_02 = System.currentTimeMillis();
        log.info("time:{}",time_02 - time_01);
        log.info("hashMap:{}",hashMap.toString());
    }

}
