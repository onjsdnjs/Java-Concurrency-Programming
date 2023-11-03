package io.concurrency.chapter10.exam06;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ThenAcceptThreadExample {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture
                .supplyAsync(() -> {
//                    System.out.println("supplyAsync() is started: " + Thread.currentThread().getName());
                    return 5;
                })
                .thenAccept(result -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("thenAccept() is result: " + result); // 작업 결과는 5
                    System.out.println("thenAccept() is started: " + Thread.currentThread().getName());
                })
                .thenAcceptAsync(result -> {
                    /*try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }*/
                    System.out.println("thenAcceptAsync() is started: " + Thread.currentThread().getName());
                })
                .thenAccept(result -> {
                    System.out.println("thenAccept() is started: " + Thread.currentThread().getName());
                });

        Thread.sleep(5000);
    }
}

