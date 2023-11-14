package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompleteOnTimeoutExample {
    public static void main(String[] args) {

        getData().completeOnTimeout("Hello Java", 1, TimeUnit.SECONDS)
        .thenAccept(result -> {
            System.out.println("결과: " + result);
        }).join();
    }

    private static CompletableFuture<String> getData() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                return "Hello World";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
