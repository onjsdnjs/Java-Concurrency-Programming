package io.concurrency.chapter09.exam01;

import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadCASExample {
    private static AtomicInteger value = new AtomicInteger(0);
    private static final int NUM_THREADS = 3;
    public static void main(String[] args) {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    int expectedValue, newValue;
                    do {
                        expectedValue = value.get();
                        newValue = expectedValue + 1;
                    } while (!value.compareAndSet(expectedValue, newValue)); // 반환 값이 false 이면 true 가 반환 될 때 까지 재시도
                    System.out.println(Thread.currentThread().getName() + ":" + expectedValue + " , " + newValue);
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Final value: " + value.get());
    }
}
