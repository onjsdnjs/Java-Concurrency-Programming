package io.concurrency.chapter11.exam04;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class SupplyAsyncExample {
    public static void main(String[] args) {

        MyService service = new MyService();

        CompletableFuture<List<Integer>> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " 가 비동기 작업을 시작합니다");
            return service.getData();
        });

        List<Integer> result = cf.join();
        result.stream().forEach(r->System.out.println(r));

        System.out.println("===============================================================================");

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<List<Integer>> future = executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " 가 비동기 작업을 시작합니다");
            return service.getData();
        });

        try {
            List<Integer> result2 = future.get();
            result2.stream().forEach(r->System.out.println(r));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("메인 스레드 종료");
    }
}

class MyService{

    public List<Integer> getData(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Arrays.asList(1,2,3);
    }
}
