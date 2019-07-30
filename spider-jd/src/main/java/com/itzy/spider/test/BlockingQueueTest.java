package com.itzy.spider.test;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author: ZY
 * @Date: 2019/7/30 10:26
 * @Version 1.0
 */
public class BlockingQueueTest {

    public static void main(String[] args) throws Exception {
        final ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(100);

        new Thread(() -> {
            while (true) {
                // remove poll take(线程安全)
                String element;
                try {
                    element = arrayBlockingQueue.take();
                    System.out.println("线程编号1" + element);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }).start();

        new Thread(() -> {
            while (true) {
                // remove poll take(线程安全)
                String element;
                try {
                    element = arrayBlockingQueue.take();
                    System.out.println("线程编号2" + element);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }).start();
        // 创建阻塞队列
        for (int i = 0; i <= 200; i++) {
            // add offer put（线程安全的）
            arrayBlockingQueue.put("element" + i);
        }


    }
}
