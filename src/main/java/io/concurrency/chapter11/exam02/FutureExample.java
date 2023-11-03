package io.concurrency.chapter11.exam02;

import java.util.concurrent.*;

public class FutureExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Future<Integer> future1 = executorService.submit(new Service1());
        Future<Integer> future2 = executorService.submit(new Service2(future1));
        Future<Integer> future3 = executorService.submit(new Service3(future2));
        Future<Integer> future4 = executorService.submit(new Service4(future3));
        Future<Integer> future5 = executorService.submit(new Service5(future4));

        // 최종 결과를 얻기 위해 future5의 완료를 기다림
        int finalResult = future5.get();

        executorService.shutdown();

        System.out.println("최종 결과: " + finalResult);
    }

    static class Service1 implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("Service 1 시작");
            // 비동기 서비스 1의 작업 수행
            return 1;
        }
    }

    static class Service2 implements Callable<Integer> {
        private Future<Integer> future;

        Service2(Future<Integer> future) {
            this.future = future;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println("Service 2 시작");
            // 비동기 서비스 2의 작업 수행 (future 결과 사용)
            return future.get() + 2;
        }
    }

    static class Service3 implements Callable<Integer> {
        private Future<Integer> future;

        Service3(Future<Integer> future) {
            this.future = future;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println("Service 3 시작");
            // 비동기 서비스 3의 작업 수행 (future 결과 사용)
            return future.get() * 3;
        }
    }

    static class Service4 implements Callable<Integer> {
        private Future<Integer> future;

        Service4(Future<Integer> future) {
            this.future = future;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println("Service 4 시작");
            // 비동기 서비스 4의 작업 수행 (future 결과 사용)
            return future.get() - 4;
        }
    }

    static class Service5 implements Callable<Integer> {
        private Future<Integer> future;

        Service5(Future<Integer> future) {
            this.future = future;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println("Service 5 시작");
            // 비동기 서비스 5의 작업 수행 (future 결과 사용)
            return future.get() + 5;
        }
    }
}
