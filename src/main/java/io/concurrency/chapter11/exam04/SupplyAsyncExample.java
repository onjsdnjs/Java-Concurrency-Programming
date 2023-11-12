package io.concurrency.chapter11.exam04;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SupplyAsyncExample {
    public static void main(String[] args) {

        MyService myService = new MyService();

        List<Integer> result = CompletableFuture.supplyAsync(() -> {

            System.out.println(Thread.currentThread().getName() + " 가 비동기 작업을 시작 합니다.");
            return myService.getData();

        }).join();

        result.stream().forEach(System.out::println);
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
