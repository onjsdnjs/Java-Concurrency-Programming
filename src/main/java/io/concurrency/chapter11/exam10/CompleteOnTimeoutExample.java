package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompleteOnTimeoutExample {
    public static void main(String[] args) {

        CompletableFuture<String> queryFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2); // 2초 대기
                return "hello world";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // completedOnTimeout을 사용하여 지정된 시간 내에 결과가 없으면 기본값을 반환
        CompletableFuture<String> resultFuture = queryFuture
                .completeOnTimeout("hello java", 1, TimeUnit.SECONDS); // 2초 내에 결과가 없으면 타임 아웃 발생

        // 결과 처리
        resultFuture.thenAccept(result -> {
            System.out.println("결과: " + result);
        }).join();
    }

}
