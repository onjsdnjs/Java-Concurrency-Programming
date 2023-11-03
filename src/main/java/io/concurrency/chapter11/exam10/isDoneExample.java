package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class isDoneExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<Integer> firstFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                return 42;
            } catch (InterruptedException e) {
                return -1;
            }
        });

        CompletableFuture<Integer> secondFuture = firstFuture.thenApplyAsync(result -> {
            try {
                Thread.sleep(1000);
                return result * 2;
            } catch (InterruptedException e) {
                return -1;
            }
        });

        while (!firstFuture.isDone() || !secondFuture.isDone()) {
            System.out.println("작업이 아직 완료되지 않았습니다.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 결과 가져오기
        int firstResult = firstFuture.get();
        int secondResult = secondFuture.get();

        System.out.println("첫 번째 결과: " + firstResult);
        System.out.println("두 번째 결과: " + secondResult);
    }
}
