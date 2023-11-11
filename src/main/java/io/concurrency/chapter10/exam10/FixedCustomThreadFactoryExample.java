package io.concurrency.chapter10.exam10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FixedCustomThreadFactoryExample {
    public static void main(String[] args) {

        ThreadFactory threadFactory = new CustomThreadFactory("CustomThread");
        ExecutorService executor = Executors.newFixedThreadPool(3, threadFactory);

        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            final int taskNumber = i;
            Callable<Integer> task = () -> {
                System.out.println("Thread : " + Thread.currentThread().getName() + ", Result: " + taskNumber + 1);
                return taskNumber + 1;
            };

            Future<Integer> future = executor.submit(task);
            futures.add(future);
        }

        for (Future<Integer> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }

    static class CustomThreadFactory implements ThreadFactory {
        private final String name;
        private int threadCount = 0;

        public CustomThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(Runnable r) {
            threadCount++;
            String threadName = name + "-" + threadCount;
            Thread newThread = new Thread(r, threadName);
            System.out.println("스레드 이름: " + threadName);
            return newThread;
        }
    }
}
