package io.concurrency.chapter08.exam03;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    public static void main(String[] args) {

        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        SharedData sharedData = new SharedData();

        // 읽기 작업을 수행하는 스레드 1
        Thread readerThread1 = new Thread(() -> {
            readWriteLock.readLock().lock();
            try {
                System.out.println("리더 스레드 1이 데이터를 읽고 있습니다. 데이터: " + sharedData.getData());
                Thread.sleep(1000); // 읽기 작업 수행
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readWriteLock.readLock().unlock();
            }
        });

        // 읽기 작업을 수행하는 스레드 2
        Thread readerThread2 = new Thread(() -> {
            readWriteLock.readLock().lock();
            try {
                System.out.println("리더 스레드 2가 데이터를 읽고 있습니다. 데이터: " + sharedData.getData());
                Thread.sleep(1000); // 읽기 작업 수행
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readWriteLock.readLock().unlock();
            }
        });

        // 쓰기 작업을 수행하는 스레드
        Thread writerThread = new Thread(() -> {
            readWriteLock.writeLock().lock();
            try {
                System.out.println("라이터 스레드가 데이터를 쓰고 있습니다.");
                sharedData.setData(42); // 데이터를 변경
                Thread.sleep(2000); // 쓰기 작업 수행
                System.out.println("라이터 스레드가 데이터를 변경했습니다. 데이터: " + sharedData.getData());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readWriteLock.writeLock().unlock();
            }
        });

        readerThread1.start();
        readerThread2.start();
        writerThread.start();
    }

    static class SharedData {
        private int data = 0;

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }
    }
}
