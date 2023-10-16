package io.concurrency.chapter09.exam02;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanExample {
    private static AtomicBoolean flag = new AtomicBoolean(false);

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                while (!flag.compareAndSet(false, true)) {
                    System.out.println("Thread 1 Busy waiting...: " + flag.get());
                }
                // Critical section
                System.out.println("Thread 1 - Critical Section");
                flag.set(false); // 이 구문이 없으면 계속 대기한다
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                while (!flag.compareAndSet(false, true)) {
                    System.out.println("Thread 2 Busy waiting...: " + flag.get());
                }
                // Critical section
                System.out.println("Thread 2 - Critical Section");
                flag.set(false); // 이 구문이 없으면 계속 대기한다
            }
        });

        thread1.start();
        thread2.start();
    }
}
