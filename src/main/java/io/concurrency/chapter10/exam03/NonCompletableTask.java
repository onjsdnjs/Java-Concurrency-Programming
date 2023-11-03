package io.concurrency.chapter10.exam03;

import java.util.concurrent.*;

public class NonCompletableTask {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<Integer> task = () -> {
            int result = performTask();
            return result;  // 결과 리턴 후 완료 됨
        };

        Future<Integer> future = executorService.submit(task);

        try {
            int result = future.get() + 10;
            System.out.println("작업 완료: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
    private static int performTask(){
        return 5;
    }
}
