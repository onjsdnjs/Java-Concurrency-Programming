package io.concurrency.chapter11.exam03;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExceptionExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<Object> handle = CompletableFuture.supplyAsync(() -> 2)
                .thenApplyAsync(result -> result + 3)
//                .thenApplyAsync(result -> {throw new RuntimeException("error");})
                .handle((result, exception) -> {
                    if (exception != null) {
                        System.out.println("예외 발생: " + exception.getMessage());
                        return 0; // 예외가 발생하면 0을 반환
                    } else {
                        return result;
                    }
                });

        System.out.println("최종 결과: " + handle.join());
    }
}