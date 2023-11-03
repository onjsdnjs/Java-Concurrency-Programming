package io.concurrency.chapter10.exam04;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RunAsyncExample {

    public static void main(String[] args) {
        MyService myService = new MyService();
        CompletableFuture<Void> voidFuture = CompletableFuture.runAsync(() -> {
            // 비동기 작업 시뮬레이션
            CompletableFuture<List<Integer>> future = myService.fetchDataAsync();
            future.join().stream().forEach(System.out::println);
        });
        System.out.println("메인 작업 수행 중..");

        voidFuture.join();

    }

    static class MyService {

        public CompletableFuture<List<Integer>> fetchDataAsync() {

            return CompletableFuture.supplyAsync(() -> {
                // 비동기 작업 시뮬레이션
                List<Integer> result = new ArrayList<>();
                result.add(1);
                result.add(2);
                result.add(3);

                try {
                    Thread.sleep(1000); // 비동기 작업 시간 지연 시뮬레이션
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                return result;
            });
        }
    }
}
