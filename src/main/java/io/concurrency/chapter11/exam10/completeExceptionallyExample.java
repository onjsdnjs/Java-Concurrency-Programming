package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;

public class completeExceptionallyExample {
    public static void main(String[] args) {

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "Hello World");
        getData(cf1);

        CompletableFuture<String> cf2 = cf1
                .thenApply(result -> {
                    System.out.println(result);
                    return result.toUpperCase();
                })
                .handle((r, e) -> {
                    if (e != null) {
                        System.err.println("Exception: " + e.getMessage());
                        return "noname";
                    }
                    return r;
                });


        System.out.println("result: " + cf2.join());
    }

    static void getData(CompletableFuture<String> future) {
        try {
            throw new RuntimeException("error");
        } catch (RuntimeException e) {
            future.completeExceptionally(e);
        }
    }
}
