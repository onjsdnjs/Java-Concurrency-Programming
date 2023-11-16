package io.concurrency.chapter11.exam12;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class ThreadPoolChainedExample {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors()-1);

       CompletableFuture.supplyAsync(() -> {
            System.out.println("IO Bound 작업: " + Thread.currentThread().getName());
            return 1;
        }, executor)

       .thenApplyAsync(res -> {
            System.out.println("CPU Bound 작업: " + Thread.currentThread().getName());
            return res + 1;
        })

        .thenApplyAsync(res -> {
            System.out.println("IO Bound 작업: " + Thread.currentThread().getName());
            return res + 2;

        },executor)

        .thenApplyAsync(res -> {
            System.out.println("CPU Bound 작업: " + Thread.currentThread().getName());
            return res + 3;
        })

        .thenComposeAsync(res -> {
            System.out.println("병렬 처리: " + Thread.currentThread().getName());
            return CompletableFuture.supplyAsync(() -> res + 4);

        })

        .thenAcceptAsync(res -> {
            System.out.println("병렬 처리: " + Thread.currentThread().getName());
            System.out.println("최종 결과 : " + res);
        }).join();

        executor.shutdown();
        forkJoinPool.shutdown();
    }
}
