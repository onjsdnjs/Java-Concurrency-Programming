package io.concurrency.chapter01.exam03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOBoundExample {
    public static void main(String[] args) {
        int numThreads = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < 5; j++) {
                        Files.readAllLines(Path.of("/Users/jeongsuwon/projects/ReactiveProgramming/src/main/java/io/reactive/reactiveprogramming/chapter01/exam04/sample.txt"));
                        System.out.println("스레드: " + Thread.currentThread().getName() +", " +j); // IO Bound 일때 ContextSwitching 이 일어난다
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
    }
}
