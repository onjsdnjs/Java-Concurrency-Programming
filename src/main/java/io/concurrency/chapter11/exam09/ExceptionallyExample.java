package io.concurrency.chapter11.exam09;


import java.util.concurrent.CompletableFuture;

public class ExceptionallyExample {
    public static void main(String[] args) {


        CompletableFuture<Object> cf = CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(500);
                        throw new RuntimeException("error");

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return 10;
                })
                .thenApply(r -> r + 20)
                .exceptionally(ex -> {
                    System.err.println("최종 예외 처리: " + ex.getMessage());
                    return -1;
                }).thenApply(r -> {
                    throw new IllegalArgumentException("error");
                }).exceptionally(ex -> {
                    System.err.println("최종 예외 처리: " + ex.getMessage());
                    return -1;
                });

        System.out.println("result: " + cf.join());
    }
}
