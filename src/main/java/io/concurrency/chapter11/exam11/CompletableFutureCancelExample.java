package io.concurrency.chapter11.exam11;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureCancelExample {
    public static void main(String[] args) {
        // 첫 번째 CompletableFuture 작업 실행
        CompletableFuture<Integer> firstFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                return 42;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // 두 번째 CompletableFuture 작업 실행
        CompletableFuture<Integer> secondFuture = firstFuture.thenApplyAsync(result -> {
            try {
                Thread.sleep(1000);
                return result * 2;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // 첫 번째 CompletableFuture 작업 취소
        firstFuture.cancel(true);

        // 두 번째 CompletableFuture 가 완료될 때까지 대기
        try {
            int result = secondFuture.get();
            System.out.println("두 번째 작업 결과: " + result);
        } catch (CancellationException e) {
            System.out.println("두 번째 작업은 첫 번째 작업 취소로 인해 취소 되었습니다.");
        } catch (Exception e) {
            System.err.println("작업 처리 중 오류 발생: " + e.getMessage());
        }
    }
}