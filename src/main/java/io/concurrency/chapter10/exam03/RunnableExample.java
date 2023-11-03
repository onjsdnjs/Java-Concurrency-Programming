package io.concurrency.chapter10.exam03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableExample {
    public static void main(String[] args) throws Exception {
        // 스레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // Runnable 을 사용한 예제
        Runnable runnableTask = () -> {
            System.out.println("Runnable 작업 수행 중...");
        };
        executorService.execute(runnableTask);

        // 스레드 풀 종료
        executorService.shutdown();
    }
}
