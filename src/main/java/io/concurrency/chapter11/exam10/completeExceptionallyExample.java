package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;

public class completeExceptionallyExample {
    public static void main(String[] args) {

        CompletableFuture<String> cf1 = new CompletableFuture<>();
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

    private static void getData(CompletableFuture cf) {
        try {
            System.out.println("비동기 작업 수행 중..");
            Thread.sleep(500);
//            throw new IllegalArgumentException("error");
        } catch (Exception e) {
            cf.completeExceptionally(e);
        }
        cf.complete("Hello World");
    }
}
