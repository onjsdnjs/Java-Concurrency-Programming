package io.concurrency.chapter11.exam04;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SupplyAsyncExample {

    public static void main(String[] args) {

        MyService myService = new MyService();
        CompletableFuture.supplyAsync(() -> {

            List<Integer> list = myService.getData();
            list.stream().forEach(System.out::println);
            return  list;

        }).join();

        System.out.println("메인 작업 종료");

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
