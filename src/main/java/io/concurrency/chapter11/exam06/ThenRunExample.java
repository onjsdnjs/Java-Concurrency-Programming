package io.concurrency.chapter11.exam06;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ThenRunExample {
    public static void main(String[] args) {

        MyService myService = new MyService();
        CompletableFuture<List<Integer>> asyncTask1 = CompletableFuture.supplyAsync(() -> {
                    System.out.println("thread1:" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return 40;
                })
                .thenApply(result -> {
                    System.out.println("thread2:" + Thread.currentThread().getName());
                    return myService.getData1();
                });

        // 비동기 작업 2: 로깅
        CompletableFuture<Void> asyncTask2 = asyncTask1.thenRun(() -> {
            System.out.println("thread: " + Thread.currentThread().getName());
            System.out.println("비동기 작업이 완료 되었습니다.");
        });

        // 비동기 작업 1 실행 및 결과 얻기
        List<Integer> result = asyncTask1.join();
        System.out.println("비동기 작업 1 결과: " + result);

        // asyncTask2의 thenRun 작업이 완료될 때까지 대기
        asyncTask2.join();

        System.out.println("모든 작업 완료");

    }
    static class MyService {

        public List<Integer> getData1(){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return Arrays.asList(1,2,3);
        }

        public List<Integer> getData2(){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return Arrays.asList(4,5,6);
        }
    }
}
