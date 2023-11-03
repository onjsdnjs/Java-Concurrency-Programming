package io.concurrency.chapter09.exam11;

import java.util.concurrent.*;

public class ThreadPoolExecutorExample {
    public static void main(String[] args) {
        int corePoolSize = 2; // 최소 스레드 수
        int maxPoolSize = 4; // 최대 스레드 수
        long keepAliveTime = 10; // 유휴 시간 (초)
        int taskNum = 9;
//        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(); // 작업 큐
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(4); // 작업 큐

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

        for (int i = 0; i < taskNum; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("Task " + taskId + " is executing on " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }
}
