package io.concurrency.chapter08.exam02;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptiblyExample {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Thread thread1 = new Thread(() -> {
            try {
                lock.lockInterruptibly(); // 락을 시도하며, 인터럽트가 들어오면 중단
                try {
                    System.out.println("스레드 1이 락을 획득했습니다");
                } finally {
                    lock.unlock();
                    System.out.println("스레드 1이 락을 해제했습니다");
                }
            } catch (InterruptedException e) {
                System.out.println("스레드 1이 인터럽트를 받았습니다");
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                lock.lockInterruptibly(); // 락을 시도하며, 인터럽트가 들어오면 중단
                try {
                    System.out.println("스레드 2가 락을 획득했습니다");
                } finally {
                    lock.unlock();
                    System.out.println("스레드 2가 락을 해제했습니다");
                }
            } catch (InterruptedException e) {
                System.out.println("스레드 2가 인터럽트를 받았습니다");
            }
        });

        thread1.start();
        thread2.start();
        thread1.interrupt();
//        thread2.interrupt();

        try {
            Thread.sleep(500);
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
