package io.concurrency.chapter08.exam02;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockExample {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Thread thread1 = new Thread(() -> {
            boolean acquired = false;
            while (!acquired) {
                acquired = lock.tryLock();
                if (acquired) {
                    try {
                        System.out.println("스레드 1이 락을 획득했습니다");
                        Thread.sleep(2000); // 스레드 1이 잠시 락을 보유
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                        System.out.println("스레드 1이 락을 해제했습니다");
                    }
                } else {
                    System.out.println("스레드 1이 락을 획득하지 못했습니다. 잠시 대기합니다.");
                    try {
                        Thread.sleep(1000); // 1초 대기 후 다시 시도
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            boolean acquired = false;
            while (!acquired) {
                acquired = lock.tryLock();
                if (acquired) {
                    try {
                        System.out.println("스레드 2가 락을 획득했습니다");
                    } finally {
                        lock.unlock();
                        System.out.println("스레드 2가 락을 해제했습니다");
                    }
                } else {
                    System.out.println("스레드 2가 락을 획득하지 못했습니다. 잠시 대기합니다.");
                    try {
                        Thread.sleep(1000); // 1초 대기 후 다시 시도
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
