package io.concurrency.chapter11.exam06;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletablePipelineExample {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        long started = System.currentTimeMillis();

        CompletableFuture
                .supplyAsync(() -> {
                    try {
                        Thread.sleep(500); // 비동기 작업 시간 지연 시뮬레이션
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("supplyAsync() is started: " + Thread.currentThread().getName());
                    return 5;
                },executor)
                .thenApplyAsync(result -> {
                    try {
                        Thread.sleep(500); // 비동기 작업 시간 지연 시뮬레이션
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("thenApply() is started: " + Thread.currentThread().getName());
                    return result * 2;
                },executor)
                .thenAcceptAsync(result -> {
                    try {
                        Thread.sleep(500); // 비동기 작업 시간 지연 시뮬레이션
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("thenAccept() is started: " + Thread.currentThread().getName());
                },executor)
                .thenRunAsync(() -> {
                    try {
                        Thread.sleep(500); // 비동기 작업 시간 지연 시뮬레이션
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("thenRun() is started: " + Thread.currentThread().getName());
                },executor).join();

        System.out.println("소요 시간 : " + (System.currentTimeMillis() -  started));
    }
}

