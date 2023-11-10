package io.concurrency.chapter10.exam09;

import java.util.concurrent.*;

public class ScheduleCallableExample {
    public static void main(String[] args) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Callable<String> task = () -> {
            return "작업이 한번 실행 되고 결과를 반환 합니다.";
        };

        long delay = 3; // 3초 후에 작업 실행
        TimeUnit unit = TimeUnit.SECONDS;

        ScheduledFuture<String> future = scheduler.schedule(task, delay, unit);

        try {
            // 작업이 완료될 때까지 대기 하고 결과를 얻는다
            String result = future.get();
            System.out.println("작업 결과: " + result);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        scheduler.shutdown();
    }
}
