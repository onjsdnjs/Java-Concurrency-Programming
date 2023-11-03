package io.concurrency.chapter10.exam12;

import java.util.concurrent.*;

public class MixedThreadPoolChained {

    public static void main(String[] args) throws InterruptedException {

        // ForkJoinPool 을 사용 하는 CompletableFuture
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("cf1.supplyAsync: " + Thread.currentThread().getName());
            return "작업 1의 결과";
        });

        // ThreadPoolExecutor 를 사용 하는 CompletableFuture
        ExecutorService executor = Executors.newFixedThreadPool(2);

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("cf2.supplyAsync: " + Thread.currentThread().getName());
            return "작업 2의 결과";
        }, executor);

        // 체인으로 연결
        cf1.thenApplyAsync(res -> {
            System.out.println("cf1.thenApplyAsync: " + Thread.currentThread().getName());
            return "작업 3의 결과";

        }).thenApplyAsync(res -> {
            System.out.println("cf1.thenApplyAsync: " + Thread.currentThread().getName());
            return "작업 4의 결과";

        },executor).thenApplyAsync(res -> {
            System.out.println("cf1.thenApplyAsync: " + Thread.currentThread().getName());
            return "작업 5의 결과";

        }).thenComposeAsync(res -> {
            System.out.println("cf1.thenComposeAsync: " + Thread.currentThread().getName());
            return cf2; // 두 번째 CompletableFuture 로 연결

        }).thenAcceptAsync(res -> {
            System.out.println("cf2.thenAcceptAsync: " + Thread.currentThread().getName());
        },executor);

        // ThreadPoolExecutor 를 종료
        executor.shutdown();
        System.out.println("main");
    }
}
