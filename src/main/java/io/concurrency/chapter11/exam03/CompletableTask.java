package io.concurrency.chapter11.exam03;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableTask {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        CompletableFuture<Integer> future = new CompletableFuture<>();

        executorService.submit(() -> {
            future.complete(5); // 결과 조작 하고 즉시 완료 시킴
        });

        try {
            int result = future.get();
            System.out.println("작업 완료: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
    private static int performTask(){
        return 5;
    }
}