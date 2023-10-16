package io.concurrency.chapter10.exam05;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureIsCancelledExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Callable 작업 생성
        Callable<Integer> callableTask = () -> {
            System.out.println("비동기 작업 시작...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("비동기 작업 완료.");
            return 42;
        };

        // 작업을 제출 하고 Future 객체를 받음
        Future<Integer> future = executorService.submit(callableTask);

        // 작업 취소 시도, 결과가 완료된 경우는 효과가 없다
        boolean cancel = future.cancel(true);

        if (!future.isCancelled()) {
            try {
                Integer result = future.get();
                System.out.println("Result: " + result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            System.out.println("Task was cancelled.");
        }
        executorService.shutdown();
    }
}
