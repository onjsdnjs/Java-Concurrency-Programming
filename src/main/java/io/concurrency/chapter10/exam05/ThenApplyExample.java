package io.concurrency.chapter10.exam05;

import java.util.concurrent.CompletableFuture;

public class ThenApplyExample {
    public static void main(String[] args) {

        MyService myService = new MyService();
        CompletableFuture<Integer> asyncFuture = myService.fetchAsyncData()
                                                .thenApply(result -> {
                                                    int r = myService.performData2(result);
                                                    System.out.println("thread:" + Thread.currentThread().getName());
                                                    return r + result;
                                                }).thenApplyAsync(result -> {
                                                    int r = myService.performData2(result);
                                                    System.out.println("thread:" + Thread.currentThread().getName());
                                                    return r + result;
                                                });

        int asyncResult = asyncFuture.join(); // 가공된 비동기 작업 결과를 얻음
        System.out.println("final result: " + asyncResult);
    }

    static class MyService {

        public CompletableFuture<Integer> fetchAsyncData() {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000); // 2초 동안 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return 40;
            });
        }

        public int performData(int input) {
            return input * 2;
        }
        public int performData2(int input) {
            return input * 2;
        }
    }
}
