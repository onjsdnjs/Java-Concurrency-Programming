package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompleteExample {
    public static void main(String[] args) {
        MyService myService = new MyService();
        CompletableFuture<Integer> future = myService.performAsyncTask();

        future.thenAccept(result -> {
            System.out.println("비동기 작업 결과: " + result);
        });
    }

    static class MyService {

        public CompletableFuture<Integer> performAsyncTask() {

            ExecutorService executorService = Executors.newSingleThreadExecutor();

            CompletableFuture<Integer> future = new CompletableFuture<>();

            executorService.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2); // 2초 대기
                    int result = 42;
                    future.complete(result); // 결과를 완료시킴
                } catch (InterruptedException e) {}
            });

            return future;
        }
    }
}
