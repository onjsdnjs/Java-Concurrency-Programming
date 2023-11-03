package io.concurrency.chapter10.exam09;


import java.util.concurrent.CompletableFuture;

public class ExceptionallyExample {
    public static void main(String[] args) {

        ServiceA sa = new ServiceA();
        ServiceB sb = new ServiceB();
        ServiceC sc = new ServiceC();

        CompletableFuture<Integer> futureA = sa.fetchAsyncDataA();
        CompletableFuture<Integer> futureB = sb.fetchAsyncDataB();
        CompletableFuture<Integer> futureC = sc.fetchAsyncDataC();

        CompletableFuture<Integer> cf = CompletableFuture.allOf(futureA, futureB, futureC)
                .thenApplyAsync(v -> {
                    int resultA = futureA.join();
                    int resultB = futureB.join();
                    int resultC = futureC.join();
                    return resultA + resultB + resultC;
                }).exceptionally(ex -> {
                    System.err.println("최종 예외 처리: " + ex.getMessage());
                    return -1; // 최종 예외 처리 및 기본값 반환
                });

        System.out.println("result: " + cf.join());
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

    static class ServiceC {

        public CompletableFuture<Integer> fetchAsyncDataC() {
            // 비동기 작업 시뮬레이션
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(300);
                    throw new RuntimeException("error");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return 30;
            }).exceptionally(ex -> {
                System.err.println("ServiceC 예외 처리: " + ex.getMessage());
                return -1; // 예외 발생 시 기본값 반환
            });
        }
    }
}
