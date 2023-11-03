package io.concurrency.chapter11.exam06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ThenAcceptExample {
    public static void main(String[] args) {

        MyService myService = new MyService();
        myService.fetchAsyncData()
                .thenApply(result -> {
                    System.out.println(result);
                    return result.stream().map(i -> i * 2).toList();
                }).thenAcceptAsync(result -> {
                    result.stream().forEach(System.out::println);
                }).join();

    }

    static class MyService {

        public CompletableFuture<List<Integer>> fetchAsyncData() {
            return CompletableFuture.supplyAsync(() -> {

                List<Integer> result = new ArrayList<>();
                result.add(1);
                result.add(2);
                result.add(3);

                try {
                    Thread.sleep(500); // 비동기 작업 시간 지연 시뮬레이션
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                return result;
            });
        }
    }
}
