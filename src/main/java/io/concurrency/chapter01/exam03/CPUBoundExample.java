package io.concurrency.chapter01.exam03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CPUBoundExample {
    public static void main(String[] args) {

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
                long result = 0;
                for (long j = 0; j < 1000000000L; j++) {
                    result += j;
                }
                System.out.println("스레드: " + Thread.currentThread().getName() +", " +result); // Cpu Bound 일때 ContextSwitching 은 크게 발생하지 않는다
            });
        }
        executorService.shutdown();
    }
}
