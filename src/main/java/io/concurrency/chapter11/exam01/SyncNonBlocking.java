package io.concurrency.chapter11.exam01;

import java.util.concurrent.*;

public class SyncNonBlocking {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<String> task = () -> {
            Thread.sleep(2000);
            return "Hello Java";
        };

        // 비동기 & 논 블록킹
        Future<String> future = executor.submit(task);

        // 동기로 전환, 다른 스레드 작업 여부에 관심을 가짐
        while (!future.isDone()) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            String result = future.get();
            System.out.println("논 블록킹 작업 결과: " + result);
        } catch (InterruptedException | ExecutionException e) {
        }

        executor.shutdown();
    }
}
