package io.concurrency.chapter10.exam02;

import java.util.concurrent.Executor;

public class SyncExecutorExample {
    public static void main(String[] args) {
        Executor syncExecutor = new SyncExecutor();

        syncExecutor.execute(()->{
            System.out.println("동기 작업 1 수행 중...");
            // 작업 수행
            System.out.println("동기 작업 1 완료...");
        });

        syncExecutor.execute(()->{
            System.out.println("동기 작업 2 수행 중...");
            // 작업 수행
            System.out.println("동기 작업 1 완료...");
        });
    }

    static class SyncExecutor implements Executor{

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }
}
