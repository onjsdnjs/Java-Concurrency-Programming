package io.concurrency.chapter10.exam07;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AwaitTerminationExample {
    public static void main(String[] args) throws InterruptedException {
        // ExecutorService 생성 및 데몬 스레드로 설정
        ExecutorService executorService = Executors.newFixedThreadPool(1, runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        });

        // 작업 제출
        executorService.submit(() -> {
            while (true) {
                System.out.println("데몬 스레드 1 실행 중...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // ExecutorService 종료 요청
        executorService.shutdown();

        // ExecutorService 종료를 대기
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        System.out.println("메인 스레드 종료");
    }
}
