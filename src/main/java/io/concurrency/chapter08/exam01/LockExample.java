package io.concurrency.chapter08.exam01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    private int count = 0;
    private Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock(); // 락을 명시적으로 활성화
        try {
            count++;
        } finally {
            lock.unlock(); // 락을 해제, finally 블록에서 작성
        }
    }

    public int getCount() {
        lock.lock(); // 락을 명시적으로 활성화
        try {
            return count;
        } finally {
            lock.unlock(); // 락을 해제, finally 블록에서 작성
        }
    }

    public static void main(String[] args) {
        LockExample lockExample = new LockExample();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                lockExample.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                lockExample.increment();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Count: " + lockExample.getCount());
    }
}
