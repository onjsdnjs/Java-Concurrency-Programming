package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompleteOnTimeoutExample {
    public static void main(String[] args) {

        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return "Hello World";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<String> cf2 = cf
                .completeOnTimeout("Hello Java", 1, TimeUnit.SECONDS);

        // 결과 처리
        cf2.thenAccept(result -> {
            System.out.println("결과: " + result);
        }).join();
    }

}
