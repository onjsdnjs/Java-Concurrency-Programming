package io.concurrency.chapter11.exam09;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WhenCompleteExample {
    public static void main(String[] args) {

        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(500);
                        throw new RuntimeException("error");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return 10;
                })
                .whenComplete((r, e) -> {
                    if (e != null) {
                        System.err.println("예외 발생: " + e.getMessage());
                        // 예외 처리
                    } else {
                        // 결과 처리
                        System.out.println("결과: " + r);
                    }
                });

        try {
            cf.get();
        } catch (InterruptedException ex) {
            System.out.println("예외 처리: " + ex.getMessage());

        } catch (ExecutionException ex) {
            System.out.println("예외 처리: " + ex.getMessage());
        }
    }
}
