package io.concurrency.chapter11.exam08;

import java.util.concurrent.CompletableFuture;

public class AllOfExample {
    public static void main(String[] args) throws InterruptedException {

        ServiceA sa = new ServiceA();
        ServiceB sb = new ServiceB();
        ServiceC sc = new ServiceC();

        CompletableFuture<Integer> cf1 = sa.getData1();
        CompletableFuture<Integer> cf2 = sb.getData2();
        CompletableFuture<Integer> cf3 = sc.getData3();

        long started = System.currentTimeMillis();
        CompletableFuture<Void> voidCf = CompletableFuture.allOf(cf1, cf2, cf3);
        CompletableFuture<Integer> finalCf = voidCf.thenApply(v -> {

            int result1 = cf1.join();
            int result2 = cf2.join();
            int result3 = cf3.join();

            System.out.println("result1 = " + result1);
            System.out.println("result2 = " + result2);
            System.out.println("result3 = " + result3);

            return result1 + result2 + result3;

        });
//        Thread.sleep(2000);
        finalCf.join();
        System.out.println("최종 소요 시간: " + (System.currentTimeMillis() - started));

//        System.out.println("최종결과: " + voidCf);
        System.out.println("최종결과: " + finalCf.join());
        System.out.println("메인 스레드 종료");
    }

    static class ServiceA {

        public CompletableFuture<Integer> getData1() {
            // 비동기 작업 시뮬레이션
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("비동기 작업 시작 1");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return 10;
            });
        }
    }

    static class ServiceB {

        public CompletableFuture<Integer> getData2() {
            // 비동기 작업 시뮬레이션
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(2000);
                    System.out.println("비동기 작업 시작 2");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return 20;
            });
        }
    }

    static class ServiceC {

        public CompletableFuture<Integer> getData3() {
            // 비동기 작업 시뮬레이션
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("비동기 작업 시작 3");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return 30;
            });
        }
    }
}
