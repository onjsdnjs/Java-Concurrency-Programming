package io.concurrency.chapter10.exam09;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WhenCompleteExample {
    public static void main(String[] args) {

        ServiceA sa = new ServiceA();
        ServiceB sb = new ServiceB();

        CompletableFuture<Integer> futureA = sa.fetchAsyncDataA();
        CompletableFuture<Integer> futureB = sb.fetchAsyncDataB();

        CompletableFuture<Integer> combinedResult = futureA.thenCombine(futureB, (resultA, resultB) -> {
            if (resultA == -1 || resultB == -1) {
                // 둘 중 하나라도 예외가 발생 하면 예외 처리
                throw new RuntimeException("error");
            }
            // 두 결과를 조합 하여 작업 수행
            return resultA + resultB;
        });

        CompletableFuture<Integer> cf = combinedResult.whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("예외 처리: " + ex.getMessage());
                // 예외 처리
            } else {
                // 결과 처리
                System.out.println("결과: " + result);
            }
        });
        try {
            cf.get();
        } catch (InterruptedException ex) {
            System.out.println("예외 발생 = " + ex.getMessage());

        } catch (ExecutionException ex) {
            System.out.println("예외 발생 = " + ex.getMessage());
        }
    }

    static class ServiceA {

        public CompletableFuture<Integer> fetchAsyncDataA() {
            // 비동기 작업 시뮬레이션
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(500);
                    throw new RuntimeException("error");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return 10;
            }).exceptionally(ex -> {
                System.err.println("ServiceA 예외 처리: " + ex.getMessage());
                return -1; // 예외 발생 시 기본값 반환
            });
        }
    }

    static class ServiceB {

        public CompletableFuture<Integer> fetchAsyncDataB() {
            // 비동기 작업 시뮬레이션
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return 20;
            }).exceptionally(ex -> {
                System.err.println("ServiceB 예외 처리: " + ex.getMessage());
                return -1; // 예외 발생 시 기본값 반환
            });
        }
    }
}
