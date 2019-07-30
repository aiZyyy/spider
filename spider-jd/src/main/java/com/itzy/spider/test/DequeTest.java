package com.itzy.spider.test;

import java.util.ArrayDeque;

/**
 * @Author: ZY
 * @Date: 2019/7/30 10:07
 * @Version 1.0
 */
public class DequeTest {
    public static void main(String[] args) {
        ArrayDeque queue = new ArrayDeque();
        queue.offer("春");
        queue.offer("夏");
        queue.offer("秋");
        System.out.println(queue);
        System.out.println(queue.peek());
        System.out.println(queue);
        System.out.println(queue.poll());
        System.out.println(queue);

    }
}
