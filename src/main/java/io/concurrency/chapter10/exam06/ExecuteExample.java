package io.concurrency.chapter10.exam06;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuteExample {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Runnable을 사용한 작업 실행 (execute 메서드)
        executorService.execute(() -> System.out.println("Runnable 작업 실행"));
        // 스레드 풀 종료
        executorService.shutdown();

        new Thread(() -> System.out.println("Runnable 작업 실행")).start();

    }
}
