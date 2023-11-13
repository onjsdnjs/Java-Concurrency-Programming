package io.concurrency.chapter11.exam05;

import java.util.concurrent.CompletableFuture;

public class ThenApplyExample {
    public static void main(String[] args) {

        MyService myService = new MyService();
        CompletableFuture<Integer> asyncFuture = CompletableFuture.supplyAsync(() -> {
                                                    try {
                                                        Thread.sleep(1000);
                                                    } catch (InterruptedException e) {
                                                        Thread.currentThread().interrupt();
                                                    }
                                                    return 40;
                                                })
                                                .thenApply(result -> {
                                                    int r = myService.getData1();
                                                    System.out.println("thread:" + Thread.currentThread().getName());
                                                    return r + result;
                                                }).thenApplyAsync(result -> {
                                                    int r = myService.getData2();
                                                    System.out.println("thread:" + Thread.currentThread().getName());
                                                    return r + result;
                                                });

        int asyncResult = asyncFuture.join(); // 가공된 비동기 작업 결과를 얻음
        System.out.println("final result: " + asyncResult);
    }

    static class MyService {

        public int getData1(){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 50;
        }

        public int getData2(){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 60;
        }
    }
}
