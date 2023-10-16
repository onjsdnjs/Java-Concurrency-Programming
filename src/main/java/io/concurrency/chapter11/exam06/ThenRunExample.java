package io.concurrency.chapter11.exam06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ThenRunExample {
    public static void main(String[] args) {

        MyService myService = new MyService();
        CompletableFuture<List<Integer>> asyncTask1 = myService.fetchAsyncData()
                .thenApply(result -> {
                    return result.stream().map(i -> i * 2).toList();
                });

        // 비동기 작업 2: 로깅
        CompletableFuture<Void> asyncTask2 = asyncTask1.thenRun(() -> {
            System.out.println("thread: " + Thread.currentThread().getName());
            System.out.println("비동기 작업이 완료 되었습니다.");
        });

        // 비동기 작업 1 실행 및 결과 얻기
        List<Integer> result = asyncTask1.join();
        System.out.println("비동기 작업 1 결과: " + result);

        // asyncTask2의 thenRun 작업이 완료될 때까지 대기
        asyncTask2.join();

        System.out.println("모든 작업 완료");

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
