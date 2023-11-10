package io.concurrency.chapter10.exam07;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShutDownExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 작업 제출
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " : 작업 완료");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("인터럽트 걸림");
                }
                return 42;
            });
        }

        // 스레드 풀 종료 요청
        executorService.shutdown();

        // 스레드 풀이 종료될 때까지 대기
        try {
            if (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
                // 시간 내에 종료 되지 않으면 강제로 종료
                executorService.shutdownNow();
                System.out.println("스레드 풀 강제 종료 수행");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 스레드 풀의 상태 확인
        if (executorService.isShutdown()) {
            System.out.println("스레드 풀을 종료 여부: " + executorService.isShutdown());
        }

        while(!executorService.isTerminated()) {
            System.out.println("스레드 풀 종료 중");
        }
        System.out.println("모든 작업이 완료되고 스레드 풀이 종료됨");
    }
}
