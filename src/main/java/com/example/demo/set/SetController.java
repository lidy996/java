package com.example.demo.set;

import lombok.Data;

import java.util.*;

public class SetController {

    public static void main(String[] args) {

        /**
         * 验证性能
         */
        // linkedHashSet、hashSet add 性能是一样的
//        Set<Integer> hashSet = new HashSet();
//        Set<Integer> linkedHashSet = new LinkedHashSet<>();
//
//        Long time_01 = System.currentTimeMillis();
//        for(int i = 0;i < 10000000;i++){
//            hashSet.add(i);
//        }
//        Long time_02 = System.currentTimeMillis();
//        System.out.println(time_02 - time_01);
//
//        Long time_03 = System.currentTimeMillis();
//        for(int i = 0;i < 10000000;i++){
//            linkedHashSet.add(1);
//        }
//        Long time_04 = System.currentTimeMillis();
//        System.out.println(time_04 - time_03);
//
//        Object o = new Object();
//        System.out.println(o.toString());
//        System.out.println(o);


        /**
         * 验证HashSet顺序
         */
        /*
            SetController.User(id=6, name=6)
            SetController.User(id=1, name=1)
            SetController.User(id=5, name=5)
            SetController.User(id=9, name=9)
            SetController.User(id=0, name=0)
            SetController.User(id=4, name=4)
            SetController.User(id=8, name=8)
            SetController.User(id=3, name=3)
            SetController.User(id=7, name=7)
         */
        Set<User> hashSet = new HashSet();
        for (int i = 0;i < 10;i++){
            User user = new User();
            user.setId(i);
            user.setName(i);
            hashSet.add(user);
        }
        hashSet.add(null);

        Iterator<User> iterator = hashSet.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        /**
         * 验证LinkedHashSet顺序
         */
        /*
            SetController.User(id=0, name=0)
            SetController.User(id=1, name=1)
            SetController.User(id=2, name=2)
            SetController.User(id=3, name=3)
            SetController.User(id=4, name=4)
            SetController.User(id=5, name=5)
            SetController.User(id=6, name=6)
            SetController.User(id=7, name=7)
            SetController.User(id=8, name=8)
            SetController.User(id=9, name=9)
            null
         */
        Set<User> userLinkedHashSet = new LinkedHashSet<>();
        for (int i = 0;i < 10;i++){
            User user = new User();
            user.setId(i);
            user.setName(i);
            userLinkedHashSet.add(user);
        }
        userLinkedHashSet.add(null);

        Iterator<User> userIterator = userLinkedHashSet.iterator();
        while (userIterator.hasNext()){
            System.out.println(userIterator.next());
        }

        /**
         * [-3, 2, 4, 40, 100]
         */
        TreeSet treeSet = new TreeSet();
        treeSet.add(4);
        treeSet.add(2);
        treeSet.add(-3);
        treeSet.add(100);
        treeSet.add(40);
        System.out.println(treeSet.toString());
    }
    @Data
    private static class User {

        private Integer id;

        private Integer name;
    }
}
