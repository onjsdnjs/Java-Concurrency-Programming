package io.concurrency.chapter10.exam03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableExample {
    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Runnable runnableTask = () ->{
            System.out.println("Runnable 작업 수행 중..");
            System.out.println("Runnable 작업 완료");
        };

        executorService.execute(runnableTask);

        executorService.shutdown();
    }
}
