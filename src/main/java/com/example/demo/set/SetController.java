package com.example.demo.set;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class SetController {

    public static void main(String[] args) {

        // linkedHashSet、hashSet add 性能是一样的
        Set<Integer> hashSet = new HashSet();
        Set<Integer> linkedHashSet = new LinkedHashSet<>();
        Long time_01 = System.currentTimeMillis();
        for(int i = 0;i < 10000000;i++){
            hashSet.add(1);
        }
        Long time_02 = System.currentTimeMillis();
        System.out.println(time_02 - time_01);

        Long time_03 = System.currentTimeMillis();
        for(int i = 0;i < 10000000;i++){
            linkedHashSet.add(1);
        }
        Long time_04 = System.currentTimeMillis();
        System.out.println(time_04 - time_03);

        Object o = new Object();
        System.out.println(o.toString());
        System.out.println(o);
    }
}
