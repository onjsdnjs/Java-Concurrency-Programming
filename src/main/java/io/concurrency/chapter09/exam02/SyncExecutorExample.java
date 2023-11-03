package io.concurrency.chapter09.exam02;

import java.util.concurrent.Executor;

public class SyncExecutorExample {
    public static void main(String[] args) {
        // 커스텀 Executor 생성
        Executor syncExecutor = new SyncExecutor();

        // 동기 작업 제출
        syncExecutor.execute(() -> {
            System.out.println("동기 작업 1 수행 중...");
            // 여기서 작업 수행
            System.out.println("동기 작업 1 완료.");
        });

        syncExecutor.execute(() -> {
            System.out.println("동기 작업 2 수행 중...");
            // 여기서 작업 수행
            System.out.println("동기 작업 2 완료.");
        });
    }

    static class SyncExecutor implements Executor {
        @Override
        public void execute(Runnable command) {
            // 작업을 현재 스레드에서 동기적으로 실행
            command.run();
        }
    }
}
