package io.concurrency.chapter10.exam03;

import java.util.concurrent.*;

public class CallableExample {
    public static void main(String[] args) {
        // 스레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // Callable 을 사용한 예제
        Callable<Integer> callableTask = () -> {
            System.out.println("Callable 작업 수행 중...");
            return 42;
        };
        Future<Integer> future = executorService.submit(callableTask);
        // Callable 작업 결과 가져 오기
        int result = 0;
        try {
            result = future.get();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Callable 작업 결과: " + result);

        // 스레드 풀 종료
        executorService.shutdown();
    }
}
