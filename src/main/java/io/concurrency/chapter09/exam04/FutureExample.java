package io.concurrency.chapter09.exam04;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Integer> future = executorService.submit(() -> {
            // 비동기 작업 시뮬레이션
            Thread.sleep(1000);
            return 42;
        });

        System.out.println("비동기 작업 시작");

        // 작업이 완료될 때까지 대기하고 결과를 가져옴
        int result = future.get();
        System.out.println("비동기 작업 결과: " + result);

        executorService.shutdown();
    }
}
