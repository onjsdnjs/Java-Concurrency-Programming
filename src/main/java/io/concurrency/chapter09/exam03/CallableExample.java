package io.concurrency.chapter09.exam03;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {
    public static void main(String[] args) throws Exception {
        // 스레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // Callable 을 사용한 예제
        Callable<Integer> callableTask = () -> {
            System.out.println("Callable 작업 수행 중...");
            return 42;
        };
        Future<Integer> future = executorService.submit(callableTask);

        // Callable 작업 결과 가져 오기
        int result = future.get();
        System.out.println("Callable 작업 결과: " + result);

        // 스레드 풀 종료
        executorService.shutdown();
    }
}
