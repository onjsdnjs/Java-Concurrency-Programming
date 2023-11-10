package io.concurrency.chapter10.exam06;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SubmitRunnableExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Runnable 작업을 제출 하고 작업이 완료된 후에 결과 값을 반환
        Future<Integer> future1 = executorService.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 42);

        System.out.println("비동기 작업 시작");

        // 작업이 완료될 때까지 대기 하고 결과를 가져옴
        int result1 = future1.get();
        System.out.println("비동기 작업 결과: " + result1);

        // Runnable 작업을 제출 하고 작업이 완료된 후에 결과 값을 반환
        Future<?> future2 = executorService.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Object result2 = future2.get();
        System.out.println("비동기 작업 결과: " + result2);

        // 스레드 풀 종료
        executorService.shutdown();

    }
}
