package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;

public class CompletedFutureExample {
    public static void main(String[] args) {

        CompletableFuture<String> cf1 = CompletableFuture.completedFuture("Hello World");

        CompletableFuture<String> cf2 = new CompletableFuture<>();
        cf2.complete("Hello World");

        cf1.thenAccept(user -> {
            System.out.println("결과: " + user);
        });

    }
}
