package io.concurrency.chapter11.exam05;

import java.util.concurrent.CompletableFuture;

public class ThenApplyExample {
    public static void main(String[] args) {

        MyService myService = new MyService();
        CompletableFuture<Integer> asyncFuture = CompletableFuture.supplyAsync(() -> {
                                                    System.out.println("thread1:" + Thread.currentThread().getName());
                                                    return 40;
                                                })
                                                .thenApply(result -> {
                                                    int r = myService.getData1();
                                                    System.out.println("thread2:" + Thread.currentThread().getName());
                                                    return r + result;
                                                }).thenApply(result -> {
                                                    int r = myService.getData2();
                                                    System.out.println("thread3:" + Thread.currentThread().getName());
                                                    return r + result;
                                                });

        int asyncResult = asyncFuture.join();
        System.out.println("final result: " + asyncResult);
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
