package io.concurrency.chapter10.exam08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InvokeAnyExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Callable<String>> tasks = new ArrayList<>();

        tasks.add(() -> {
            Thread.sleep(2000);
            return "Task 1";
        });
        tasks.add(() -> {
            Thread.sleep(1000);
            throw new RuntimeException("error");
        });
        tasks.add(() -> {
            Thread.sleep(3000);
            return "Task 3";
        });
        long started = 0;
        try {
            started = System.currentTimeMillis();
            String result = executor.invokeAny(tasks);
            System.out.println("result: " + result);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        System.out.println("총 소요시간:"  + (System.currentTimeMillis() - started ));
    }
}
