package io.concurrency.chapter10.exam08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class InvokeAnyExample {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Callable<String>> tasks = new ArrayList<>();

        tasks.add(() -> {
            Thread.sleep(2);
            return "Task 1";
        });
        tasks.add(() -> {
            Thread.sleep(1);
            return "Task 2";
        });
        tasks.add(() -> {
            Thread.sleep(3);
            return "Task 3";
        });

        try {

            String result = executor.invokeAny(tasks);
            System.out.println("result: " + result);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
