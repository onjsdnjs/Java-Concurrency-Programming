package io.concurrency.chapter11.exam05;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ThenApplyThreadExample {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture
                .supplyAsync(() -> {
//                    System.out.println("supplyAsync() is started: " + Thread.currentThread().getName());
                    return 5;
                })
                .thenApply(result -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("thenApply() is result: " + result); // 작업 결과는 5
                    System.out.println("thenApply() is started: " + Thread.currentThread().getName());
                    return result;
                })
                ;

        Thread.sleep(5000);
    }
}

