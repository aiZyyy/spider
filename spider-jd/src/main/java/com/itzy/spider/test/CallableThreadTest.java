package com.itzy.spider.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: ZY
 * @Date: 2019/7/30 10:23
 * @Version 1.0
 */
public class CallableThreadTest {
    public static void main(String[] args) {
        // Callable的返回值就要使用Future对象，Callable负责计算结果，Future负责拿到结果
        // 1、实现Callable接口
        Callable<Integer> callable = () -> {
            int i = 999;
            // do something
            // eg request http server and process
            return i;
        };
        // 2、使用FutureTask启动线程
        FutureTask<Integer> future = new FutureTask<>(callable);
        // 4.启动线程
        new Thread(future).start();
        // 4、获取线程的结果
        try {
            // 可能做一些事情
            Thread.sleep(5000);
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
