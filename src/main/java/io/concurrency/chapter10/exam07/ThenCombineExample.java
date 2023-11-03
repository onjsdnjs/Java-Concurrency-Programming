package io.concurrency.chapter10.exam07;

import java.util.concurrent.CompletableFuture;

public class ThenCombineExample {
    public static void main(String[] args){

        MyService myService = new MyService();

        CompletableFuture<String> cf1 = myService.fetchAsyncData(); // 비동기 작업 1
        CompletableFuture<String>  cf2 = myService.fetchAsyncData2();

        CompletableFuture<String> cf3 = cf1.thenCombine(cf2, (r1, r2) -> r1 + r2);

        CompletableFuture<String> cf4 = cf3.thenCompose(result -> CompletableFuture.supplyAsync(() -> result + " Java"));

        System.out.println("final result: " + cf4.join());
    }

    static class MyService {

        public CompletableFuture<String> fetchAsyncData() {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(500); // 2초 동안 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Hello ";
            });
        }

        public CompletableFuture<String> fetchAsyncData2() {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(500); // 2초 동안 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "World";
            });
        }

        public int performData(int input) {
            return input + 10;
        }
    }
}
