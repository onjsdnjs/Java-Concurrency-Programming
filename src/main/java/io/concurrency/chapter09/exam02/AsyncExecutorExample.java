package io.concurrency.chapter09.exam02;

import java.util.concurrent.Executor;

public class AsyncExecutorExample {
    public static void main(String[] args) {
        // 커스텀 Executor 생성
        Executor asyncExecutor = new AsyncExecutor();

        // 비동기 작업 제출
        asyncExecutor.execute(() -> {
            System.out.println("비동기 작업 1 수행 중...");
            // 여기서 비동기 작업 수행
            System.out.println("비동기 작업 1 완료.");
        });

        asyncExecutor.execute(() -> {
            System.out.println("비동기 작업 2 수행 중...");
            // 여기서 비동기 작업 수행
            System.out.println("비동기 작업 2 완료.");
        });
    }

    static class AsyncExecutor implements Executor {
        @Override
        public void execute(Runnable command) {
            // 작업을 새로운 스레드에서 비동기적으로 실행
            Thread thread = new Thread(command);
            thread.start();
        }
    }
}
