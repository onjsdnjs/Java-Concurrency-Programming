package io.concurrency.chapter10.exam12;

import java.util.concurrent.CompletableFuture;

public class ForkJoinPoolCommonPool {
    public static void main(String[] args) {

        long started = System.currentTimeMillis();
        int numTasks = (Runtime.getRuntime().availableProcessors() - 1) + 100; // 실행할 작업 수
        CompletableFuture<Void>[] futures = new CompletableFuture[numTasks];

        for (int i = 0; i < numTasks; i++) {
            int taskId = i;
            futures[i] = CompletableFuture.runAsync(() -> {
                // 간단한 CPU 바운드 작업: 매우 오래 걸린다고 가정
                System.out.println("Task " + taskId + " started.");

                try {
                    Thread.sleep(2000); // 2초 동안 스레드를 블록
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Task " + taskId + " completed.");
                System.out.println("Thread: " + Thread.currentThread().getName());
            });
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);

        // 결과 기다리고 프로그램 종료
        try {
            allOf.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("소요시간: " + (System.currentTimeMillis() - started));
    }
}
