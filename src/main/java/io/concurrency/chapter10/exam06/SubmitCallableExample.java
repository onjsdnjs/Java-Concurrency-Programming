package io.concurrency.chapter10.exam06;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SubmitCallableExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Callable 을 사용한 작업 실행 (submit 메서드)
        Future<Integer> future = executorService.submit(() -> {
            System.out.println("Callable 작업 실행");
            return 42;
        });

        // 작업 결과를 가져옴
        int result = future.get();
        System.out.println("작업 결과: " + result);

        // 스레드 풀 종료
        executorService.shutdown();

        // 순수 스레드로 구현 하는 경우
        Thread workerThread = new Thread(() -> {
            System.out.println("Runnable 작업 실행");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            int r = 42;
            System.out.println("작업 결과: " + r);
        });

        // 스레드 시작
        workerThread.start();

        // 작업을 완료할 때까지 대기
        workerThread.join();

        System.out.println("===============================================");
        System.out.println("모든 작업 완료 ");
    }
}
