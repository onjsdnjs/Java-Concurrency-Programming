package io.concurrency.chapter09.exam04;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        int numThreads = 5;
        CountDownLatch startSignal = new CountDownLatch(1); // 시작 신호
        CountDownLatch doneSignal = new CountDownLatch(numThreads); // 완료 신호

        for (int i = 0; i < numThreads; i++) {
            new Thread(new Worker(startSignal, doneSignal)).start();
        }

        Thread.sleep(3000); // 잠시 대기
        startSignal.countDown(); // 특정 작업이 완료되었음을 알리고 모든 스레드를 시작시킴
        System.out.println("startSignal is countDown");

        doneSignal.await(); // 모든 스레드의 작업이 완료될 때까지 대기

        System.out.println("All threads have completed their tasks.");
    }

    static class Worker implements Runnable {
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        public Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                startSignal.await(); // 시작 신호를 기다림
                // 스레드의 작업을 수행
                System.out.println("Thread " + Thread.currentThread().getId() + " is working.");
                Thread.sleep(3000); // 잠시 대기
                System.out.println("Thread " + Thread.currentThread().getId() + " has completed its task.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                doneSignal.countDown(); // 작업이 완료됨을 알림
            }
        }
    }
}
