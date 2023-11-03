package io.concurrency.chapter09.exam07;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShutDownExample {
    public static void main(String[] args) {
        // 스레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 작업 제출
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("작업 1 완료");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // 스레드 풀 종료 요청
        executorService.shutdown();

        // 스레드 풀이 종료될 때까지 대기
        try {
            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                // 시간 내에 종료 되지 않으면 강제로 종료
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 스레드 풀의 상태 확인
        if (executorService.isShutdown()) {
            System.out.println("스레드 풀이 종료됨");
        }

        if (executorService.isTerminated()) {
            System.out.println("모든 작업이 완료되고 스레드 풀이 종료됨");
        }
    }
}
