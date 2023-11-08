package io.concurrency.chapter08.exam03;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockAPIExample {
    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public int getQueueLength() {
        return rwLock.getQueueLength();
    }

    public int getReadHoldCount() {
        ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
        return rwLock.getReadHoldCount();
    }

    public int getReadLockCount() {
        return rwLock.getReadLockCount();
    }

    public int getWriteHoldCount() {
        ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
        return rwLock.getWriteHoldCount();
    }

    public boolean hasQueuedThread(Thread thread) {
        return rwLock.hasQueuedThread(thread);
    }

    public boolean hasQueuedThreads() {
        return rwLock.hasQueuedThreads();
    }

    public boolean isFair() {
        return rwLock.isFair();
    }

    public boolean isWriteLocked() {
        return rwLock.isWriteLocked();
    }

    public boolean isWriteLockedByCurrentThread() {
        ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
        return rwLock.isWriteLockedByCurrentThread();
    }

    public static void main(String[] args) {
        ReentrantReadWriteLockAPIExample example = new ReentrantReadWriteLockAPIExample();


        Thread thread1 = new Thread(() -> {

            rwLock.readLock().lock();
            try {
                System.out.println("Has Queued Thread: " + example.hasQueuedThread(Thread.currentThread()));
                Thread.sleep(1000); // 스레드 2가 Lock을 보유한 상태에서 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rwLock.readLock().unlock();
            }

        });

        Thread thread2 = new Thread(() -> {
            rwLock.writeLock().lock();
            try {
                System.out.println("Has Queued Thread: " + example.hasQueuedThread(Thread.currentThread()));
                Thread.sleep(1000); // 스레드 2가 Lock을 보유한 상태에서 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rwLock.writeLock().unlock();
            }

        });

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(500); // 메인 스레드 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("Queue Length: " + example.getQueueLength());
        System.out.println("Read Hold Count: " + example.getReadHoldCount());
        System.out.println("Read Lock Count: " + example.getReadLockCount());
        System.out.println("Write Hold Count: " + example.getWriteHoldCount());
        System.out.println("Has Queued Threads: " + example.hasQueuedThreads());
        System.out.println("Is Fair: " + example.isFair());
        System.out.println("Is Write Locked: " + example.isWriteLocked());
        System.out.println("Is Write Locked By Current Thread: " + example.isWriteLockedByCurrentThread());


    }
}
