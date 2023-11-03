package io.concurrency.chapter11.exam11;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WaitingOfGetExample {
    public static void main(String[] args) {

        MyService myService = new MyService();

        CompletableFuture<String> service1Future = CompletableFuture.supplyAsync(() -> myService.callService("Service1"));
        CompletableFuture<String> service2Future = CompletableFuture.supplyAsync(() -> myService.callService("Service2"));
        CompletableFuture<String> service3Future = CompletableFuture.supplyAsync(() -> myService.callService("Service3"));

        CompletableFuture<Void> allOf = CompletableFuture.allOf(service1Future, service2Future, service3Future);

        try {
            allOf.get(); // 모든 작업이 완료될 때까지 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 각 서비스의 결과를 가져올 수 있음, 결과는 모두 완료된 상태임
        try {
            String result1 = service1Future.get();
            String result2 = service2Future.get();
            String result3 = service3Future.get();

            // 결과를 처리하는 로직을 추가
            System.out.println("Service1 결과: " + result1);
            System.out.println("Service2 결과: " + result2);
            System.out.println("Service3 결과: " + result3);

            // 여기서 결과를 합치거나 다른 작업을 수행할 수 있음
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    static class MyService {
        public String callService(String serviceName) {
            return serviceName.toUpperCase();
        }
    }
}
