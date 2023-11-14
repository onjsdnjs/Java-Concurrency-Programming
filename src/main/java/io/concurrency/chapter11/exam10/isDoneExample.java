package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class isDoneExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return 42;
        });

        CompletableFuture<Integer> cf2 = cf1.thenApplyAsync(result -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return result * 2;
        });

        while (!cf1.isDone() || !cf2.isDone()) {
            System.out.println("작업이 아직 완료되지 않았습니다.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 결과 가져오기
        int firstResult = cf1.get();
        int secondResult = cf2.get();

        System.out.println("첫 번째 결과: " + firstResult);
        System.out.println("두 번째 결과: " + secondResult);
    }
}
