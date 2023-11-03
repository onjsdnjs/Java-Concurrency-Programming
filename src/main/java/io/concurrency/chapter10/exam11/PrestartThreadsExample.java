package io.concurrency.chapter10.exam11;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PrestartThreadsExample {
    public static void main(String[] args) {
        int corePoolSize = 2; // 최소 스레드 수
        int maxPoolSize = 4; // 최대 스레드 수
        long keepAliveTime = 10; // 유휴 시간 (초)
        int taskNum = 5;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(); // 작업 큐

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

        // prestartCoreThread를 사용하여 초기 스레드 1개를 미리 시작
        executor.prestartCoreThread();

        // prestartAllCoreThreads를 사용하여 모든 초기 스레드를 미리 시작
        executor.prestartAllCoreThreads();

        for (int i = 0; i < taskNum; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("Task " + taskId + " is executing on " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }
}
