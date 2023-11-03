package io.concurrency.chapter11.exam11;

import java.util.concurrent.CompletableFuture;

public class WaitingOJoinExample {
    public static void main(String[] args) {

        MyService myService = new MyService();

        CompletableFuture<String> service1Future = CompletableFuture.supplyAsync(() -> myService.callService("Service1"));
        CompletableFuture<String> service2Future = CompletableFuture.supplyAsync(() -> myService.callService("Service2"));
        CompletableFuture<String> service3Future = CompletableFuture.supplyAsync(() -> myService.callService("Service3"));

        CompletableFuture<Void> allOf = CompletableFuture.allOf(service1Future, service2Future, service3Future);
        allOf.join(); // 모든 작업이 완료될 때까지 대기

        // 각 서비스의 결과를 가져올 수 있음, 결과는 모두 완료된 상태임
        String result1 = service1Future.join();
        String result2 = service2Future.join();
        String result3 = service3Future.join();

        // 결과를 처리하는 로직을 추가
        System.out.println("Service1 결과: " + result1);
        System.out.println("Service2 결과: " + result2);
        System.out.println("Service3 결과: " + result3);

    }
    static class MyService {
        public String callService(String serviceName) {
            return serviceName.toUpperCase();
        }
    }
}
