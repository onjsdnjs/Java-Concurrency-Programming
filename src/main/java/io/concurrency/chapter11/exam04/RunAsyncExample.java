package io.concurrency.chapter11.exam04;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RunAsyncExample {
    public static void main(String[] args) {

        MyService myService = new MyService();

        CompletableFuture.runAsync(() -> {

            System.out.println(Thread.currentThread().getName() + " 가 비동기 작업을 시작 합니다.");
            List<Integer> result = myService.getData();

            result.stream().forEach(System.out::println);

        }).join();
    }

    static class MyService {

        public List<Integer> getData() {

            List<Integer> result = new ArrayList<>();
            result.add(1);
            result.add(2);
            result.add(3);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return result;
        }
    }
}
