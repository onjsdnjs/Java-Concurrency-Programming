package io.concurrency.chapter09.exam09;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleRunnableExample {
    public static void main(String[] args) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            System.out.println("작업이 한번 실행 됩니다");
        };

        long delay = 2; // 2초 후에 작업 실행
        TimeUnit unit = TimeUnit.SECONDS;

        // 주어진 시간 후에 작업을 실행
        scheduler.schedule(task, delay, unit);

        try {
            // 프로그램이 종료되지 않도록 대기
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scheduler.shutdown();
    }
}
