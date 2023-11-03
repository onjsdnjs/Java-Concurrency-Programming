package io.concurrency.chapter09.exam04;

import java.util.concurrent.*;

public class FutureCallbackExample {

    interface Callback<T> {
        void onComplete(T result);
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Callable 작업 생성
        Callable<Integer> callableTask = () -> {
            Thread.sleep(1000);
            return 42;
        };

        // Future 를 사용하여 작업을 제출 하고 결과를 가져옴
        Future<Integer> future = executorService.submit(callableTask);

        System.out.println("비동기 작업 시작");

        // 작업이 완료 되면 콜백을 호출 하여 결과 처리
        registerCallback(future, result -> {
            System.out.println("비동기 작업 결과: " + result);
        });

        // 스레드 풀 종료
        executorService.shutdown();
    }

    // 작업 완료 시 콜백을 호출
    static <T> void registerCallback(Future<T> future, Callback<T> callback) {
        Thread callbackThread = new Thread(() -> {
            try {
                T result = future.get();
                callback.onComplete(result);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        callbackThread.start();
    }
}
