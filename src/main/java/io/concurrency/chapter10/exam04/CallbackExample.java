package io.concurrency.chapter10.exam04;

import javax.security.auth.callback.Callback;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallbackExample {

    // 콜백 인터 페이스
    interface Callback {
        void onCompletion(int result);
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 비동기 작업 시작
        executorService.execute(() -> {
            // 비동기 작업 시뮬레이션
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            int result = 42;

            // 작업 완료 후 콜백 호출
            Callback callback = new MyCallback();
            callback.onCompletion(result);
        });

        System.out.println("비동기 작업 시작");

        // 스레드 풀 종료
        executorService.shutdown();
    }

    // 콜백 구현 클래스
    static class MyCallback implements Callback {
        @Override
        public void onCompletion(int result) {
            System.out.println("비동기 작업 결과: " + result);
        }
    }
}
