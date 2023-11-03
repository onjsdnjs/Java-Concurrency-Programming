package io.concurrency.chapter10.exam12;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolCustomPool {
    public static void main(String[] args) {
        // 커스텀 ForkJoinPool 생성
        ForkJoinPool customThreadPool = new ForkJoinPool(4); // 예: 4개의 스레드

        // CompletableFuture에서 커스텀 ForkJoinPool 사용
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
                    // I/O 작업 시뮬레이션
                    IOOperation();
                    return "Result of I/O operation";
                }, customThreadPool)
                .thenApply(result -> {
                    System.out.println("Received result: " + result);
                    return "Processed result: " + result;
                })
                .thenAccept(processedResult -> {
                    System.out.println("Final result: " + processedResult);
                });

        // 결과 기다리고 프로그램 종료
        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 커스텀 ForkJoinPool을 종료 (프로그램 종료 전에 수동으로 종료해야 함)
        customThreadPool.shutdown();
    }

    // I/O 작업 시뮬레이션
    private static void IOOperation() {
        try {
            Thread.sleep(2000); // 2초 동안 I/O 작업 시뮬레이션
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
