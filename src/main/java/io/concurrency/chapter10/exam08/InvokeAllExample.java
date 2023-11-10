package io.concurrency.chapter10.exam08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class InvokeAllExample {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Callable<Integer>> tasks = new ArrayList<>();

        tasks.add(() -> {
            Thread.sleep(3000);
            return 1;
        });
        tasks.add(() -> {
            Thread.sleep(2000);
            return 1;
        });
        tasks.add(() -> {
            throw new RuntimeException("invokeAll");
        });

        long started = 0;
        try {
            started = System.currentTimeMillis();
            List<Future<Integer>> results = executor.invokeAll(tasks);
            for (Future<Integer> future : results) {
                try {
                    Integer value = future.get();
                    System.out.println("result: " + value);
                } catch (ExecutionException e) {
                    // 작업 중 예외가 발생한 경우 처리
                    Throwable cause = e.getCause();
                    if (cause instanceof RuntimeException) {
                        System.err.println("exception: " + cause.getMessage());
                    } else {
                        e.printStackTrace();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            executor.shutdown();
        }

        System.out.println("총 소요시간:"  + (System.currentTimeMillis() - started ));
    }
}
