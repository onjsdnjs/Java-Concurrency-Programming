package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;

public class completeExceptionallyExample {
    public static void main(String[] args) {
        // CompletableFuture 를 생성
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "hell world");
        networkCall(future);

        // CompletableFuture 의 결과를 처리 하고 예외 처리
        future
            .thenApply(result -> {
                System.out.println(result);
                return result.toUpperCase();
            })
            .handle((result, throwable) -> {
                if (throwable != null) {
                    System.err.println("네트 워크 호출 에서 예외 발생: " + throwable.getMessage());
                } else {
                    System.out.println("네트 워크 호출 결과: " + result);
                }
                return null;
            });
    }

    static void networkCall(CompletableFuture<String> future) {
        try {
            // 예외 발생 시 completeExceptionally()를 사용 하여 예외를 설정
            throw new NetworkException("네트 워크 호출 실패");
        } catch (NetworkException e) {
            future.completeExceptionally(e);
        }
    }

    static class NetworkException extends Exception {
        public NetworkException(String message) {
            super(message);
        }
    }
}
