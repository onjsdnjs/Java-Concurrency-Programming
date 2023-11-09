package io.concurrency.chapter10.exam04;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallbackExample {

    interface Callback{
        void onComplete(int result);
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int result = 42;

            Callback callback = new MyCallback();
            callback.onComplete(result);
        });

        System.out.println("비동기 작업 시작");
    }

    static class MyCallback implements Callback{

        @Override
        public void onComplete(int result) {
            System.out.println("비동기 작업 결과: " + result);
        }
    }
}
