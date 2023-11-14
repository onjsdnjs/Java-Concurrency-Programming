package io.concurrency.chapter11.exam06;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ThenAcceptExample {

    public static void main(String[] args) {

        MyService myService = new MyService();
        CompletableFuture.supplyAsync(() -> {
                    System.out.println("thread1:" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return 40;
                })
                .thenAccept(result -> {
                    System.out.println("thread2:" + Thread.currentThread().getName());
                    System.out.println("결과: " + result);
                    List<Integer> r = myService.getData1();
                    r.forEach(System.out::println);

                }).thenAcceptAsync(result -> {
                    System.out.println("thread3:" + Thread.currentThread().getName());
                    System.out.println("결과: " + result);
                    List<Integer> r = myService.getData2();
                    r.forEach(System.out::println);

                }).join();
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


