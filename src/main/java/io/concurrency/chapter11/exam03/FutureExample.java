package io.concurrency.chapter11.exam03;

import java.util.concurrent.*;

public class FutureExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<Integer> task1 = () -> 2;
        Callable<Integer> task2 = () -> {
            int result = task1.call() + 3;
            return result;
        };

        Future<Integer> future2 = executorService.submit(task2);

        int intermediateResult;
        try {
            intermediateResult = future2.get();

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("예외 발생: " + e.getMessage());
            intermediateResult = 0; // 예외가 발생하면 0을 반환
        }

        int finalResult = intermediateResult;

        System.out.println("최종 결과: " + finalResult);
        executorService.shutdown();
    }
}
