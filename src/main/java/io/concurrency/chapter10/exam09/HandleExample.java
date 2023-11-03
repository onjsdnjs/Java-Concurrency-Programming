package io.concurrency.chapter10.exam09;

import java.util.concurrent.CompletableFuture;

public class HandleExample {
    public static void main(String[] args) {

        ServiceA sa = new ServiceA();
        ServiceB sb = new ServiceB();

        CompletableFuture<Integer> futureA = sa.fetchAsyncDataA()
                .handle((resultA, exA) -> {
                    if (exA != null) {
                        System.err.println("ServiceA 예외 처리: " + exA.getMessage());
                        return -1; // 예외 발생 시 기본값 반환
                    }
                    return resultA;
                });

        CompletableFuture<Integer> futureB = sb.fetchAsyncDataB()
                .handle((resultB, exB) -> {
                    if (exB != null) {
                        System.err.println("ServiceB 예외 처리: " + exB.getMessage());
                        return -1; // 예외 발생 시 기본값 반환
                    }
                    return resultB;
                });

        CompletableFuture<Integer> combinedResult = futureA.thenCombine(futureB, (resultA, resultB) -> {
            if (resultA == -1 || resultB == -1) {
                // 둘 중 하나라도 예외가 발생하면 예외 처리
                return -1;
            }
            // 두 결과를 조합하여 복잡한 작업 수행 (예시로 간단한 덧셈)
            return resultA + resultB;
        });

        System.out.println("result: " + combinedResult.join());
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
