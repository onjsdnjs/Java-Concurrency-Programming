package io.concurrency.chapter08.exam02;

import java.util.concurrent.locks.ReentrantLock;

public class LockAPIExample {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1000); // 스레드 1이 Lock을 보유한 상태에서 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1000); // 스레드 2가 Lock을 보유한 상태에서 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(500); // 메인 스레드 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Lock에 대한 정보 출력
        System.out.println("Hold Count: " + lock.getHoldCount());
        System.out.println("Is Held By Current Thread: " + lock.isHeldByCurrentThread());
        System.out.println("Has Queued Threads: " + lock.hasQueuedThreads());
        System.out.println("has Queued Thread1: " + lock.hasQueuedThread(thread1));
        System.out.println("has Queued Thread2: " + lock.hasQueuedThread(thread2));
        System.out.println("Queue Length: " + lock.getQueueLength());
        System.out.println("isFair: " + lock.isFair());

    }
}
