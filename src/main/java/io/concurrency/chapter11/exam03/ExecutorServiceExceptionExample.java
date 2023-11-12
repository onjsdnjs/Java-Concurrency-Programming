package io.concurrency.chapter11.exam03;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceExceptionExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<Integer> future1 = executorService.submit(() -> 2);
        Future<Integer> future2 = executorService.submit(() -> {
            return future1.get() + 3;
//            throw new RuntimeException("error");
        });

        int result;
        try {
            result = future2.get();

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("예외 발생: " + e.getMessage());
            result = 0; // 예외가 발생하면 0을 반환
        }

        int finalResult = result;

        System.out.println("최종 결과: " + finalResult);
        executorService.shutdown();
    }
}
