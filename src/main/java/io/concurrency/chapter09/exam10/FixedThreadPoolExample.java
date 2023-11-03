package io.concurrency.chapter09.exam10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolExample {
    public static void main(String[] args) {

        int threadNum = 3;
        int taskNum = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

        for (int i = 1; i <= taskNum; i++) {
            executorService.submit(() -> {
                System.out.println("Thread : " + Thread.currentThread().getName());
            });
        }

        executorService.shutdown();
    }
}
