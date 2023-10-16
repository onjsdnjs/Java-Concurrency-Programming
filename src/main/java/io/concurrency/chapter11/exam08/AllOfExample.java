package io.concurrency.chapter11.exam08;

import java.util.concurrent.CompletableFuture;

public class AllOfExample {
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
                });

        System.out.println("result: " + cf.join());
    }

    static class ServiceA {

        public CompletableFuture<Integer> fetchAsyncDataA() {
            // 비동기 작업 시뮬레이션
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return 10;
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
            });
        }
    }

    static class ServiceC {

        public CompletableFuture<Integer> fetchAsyncDataC() {
            // 비동기 작업 시뮬레이션
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return 30;
            });
        }
    }
}
