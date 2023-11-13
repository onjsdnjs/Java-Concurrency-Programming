package io.concurrency.chapter11.exam05;

import java.util.concurrent.CompletableFuture;

public class ThenApplyExample {
    public static void main(String[] args) {

        MyService myService = new MyService();
        long started = System.currentTimeMillis();
        CompletableFuture<Integer> asyncFuture = CompletableFuture.supplyAsync(() -> {
                                                    System.out.println("thread1:" + Thread.currentThread().getName());
                                                    try {
                                                        Thread.sleep(500);
                                                    } catch (InterruptedException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                    return 40;
                                                })
                                                .thenApply(result -> {
                                                    System.out.println("thread2:" + Thread.currentThread().getName());
                                                    int r = myService.getData1();
                                                    return r + result;
                                                }).thenApplyAsync(result -> {
                                                    System.out.println("thread3:" + Thread.currentThread().getName());
                                                    int r = myService.getData2();
                                                    return r + result;
                                                });

        int asyncResult = asyncFuture.join();
        System.out.println("final result: " + asyncResult);
        System.out.println("총 소요 시간: " + (System.currentTimeMillis() - started));
    }

    static class MyService {

        public int getData1(){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 50;
        }

        public int getData2(){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 60;
        }
    }
}
