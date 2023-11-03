package io.concurrency.chapter09.exam08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class InvokeAllExample {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Callable<Integer>> tasks = new ArrayList<>();

        tasks.add(() -> 1);
        tasks.add(() -> 2);
        tasks.add(() -> {
            throw new RuntimeException("invokeAll");
        });

        try {
            List<Future<Integer>> results = executor.invokeAll(tasks);

            for (Future<Integer> future : results) {
                boolean done = future.isDone();
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
    }
}
