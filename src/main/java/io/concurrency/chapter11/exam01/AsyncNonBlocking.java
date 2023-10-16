package io.concurrency.chapter11.exam01;

import java.util.concurrent.*;

public class AsyncNonBlocking {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<String> task = () -> {
            Thread.sleep(2000);
            return "Hello Java";
        };
        // 비동기 & 논 블록킹
        Future<String> future = executor.submit(task);

        // 메인 작업 계속 수행
        int sum = 0;
        for (int i = 0; i < 1000000; i++) {
            sum = sum + i;
        }

        try {
            String result = future.get();
            System.out.println("논블록킹 작업 결과: " + result);
        } catch (InterruptedException | ExecutionException e) {
        }

        executor.shutdown();
    }
}
