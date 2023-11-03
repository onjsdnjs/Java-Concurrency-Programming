package io.concurrency.chapter08.exam02;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {
    private static final int NUM_THREADS = 5;
    private static final int NUM_INCREMENTS = 1000_000;
    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < NUM_INCREMENTS; j++) {
                    counter.incrementAndGet();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int finalValue = counter.get();
        System.out.println("Final counter value: " + finalValue);
    }
}
