package io.concurrency.chapter09.exam02;

import java.util.concurrent.atomic.AtomicInteger;

public class NonAtomicIntegerExample {
    private static final int NUM_THREADS = 5;
    private static final int NUM_INCREMENTS = 1000_000;
    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < NUM_INCREMENTS; j++) {
                    counter++; // 일반 변수를 증가시키는 연산
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Final counter value: " + counter);
    }
}
