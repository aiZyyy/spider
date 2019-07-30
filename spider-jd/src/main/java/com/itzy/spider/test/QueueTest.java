package com.itzy.spider.test;

import java.util.ArrayDeque;

/**
 * @Author: ZY
 * @Date: 2019/7/30 9:58
 * @Version 1.0
 */
public class QueueTest {
    public static void main(String[] args) {

        // test1();
        final ArrayDeque<String> arrayDeque = new ArrayDeque<>();
        for (int i = 0; i <= 99; i++) {
            arrayDeque.offer("element" + i);
        }

        new Thread(() -> {
            while (true) {
                String element = arrayDeque.poll();
                System.out.println("线程编号：" + Thread.currentThread().getId() + "  " + element);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }).start();

        new Thread(() -> {
            while (true) {
                String element = arrayDeque.poll();
                System.out.println("线程编号：" + Thread.currentThread().getId() + "  " + element);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }).start();

        new Thread(() -> {
            while (true) {
                String element = arrayDeque.poll();
                System.out.println("线程编号：" + Thread.currentThread().getId() + "  " + element);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }).start();

    }

    private static void test1() {
        ArrayDeque<String> arrayDeque = new ArrayDeque<String>();
        // 插入一个元素
        arrayDeque.offer("element01");
        arrayDeque.offer("element02");
        arrayDeque.offer("element03");
        arrayDeque.offer("element04");
        // 获取元素
        String element = arrayDeque.poll();
        System.out.println(element);
        element = arrayDeque.poll();
        System.out.println(element);
    }
}
