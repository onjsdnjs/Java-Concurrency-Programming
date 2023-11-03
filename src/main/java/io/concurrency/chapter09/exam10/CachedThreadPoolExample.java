package io.concurrency.chapter09.exam10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolExample {
    public static void main(String[] args) {

        int taskNum = 20;
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 1; i <= taskNum; i++) {
            executorService.submit(() -> {
                System.out.println("Thread : " + Thread.currentThread().getName());
            });
        }

        executorService.shutdown();
    }
}
