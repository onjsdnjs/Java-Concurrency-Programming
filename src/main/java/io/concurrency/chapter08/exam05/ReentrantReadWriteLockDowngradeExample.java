package io.concurrency.chapter08.exam05;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDowngradeExample {
    public static void main(String[] args) {

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Lock readLock = lock.readLock();
        Lock writeLock = lock.writeLock();
        SharedData sharedData = new SharedData();

        // 여러 개의 스레드가 쓰기 락을 사용하는 경우
        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                writeLock.lock();
                try {
                    System.out.println("쓰기 락 획득: " + Thread.currentThread().getName());

                    // 임계 영역 수행
                    sharedData.setData((int) (Math.random() * 100));
                    System.out.println("데이터 업데이트");

                    // 쓰기 락을 읽기 락으로 다운그레이드
                    readLock.lock();
                    System.out.println("다운그레이드: 쓰기 락 -> 읽기 락");
                    // 쓰기 락 해제
                    writeLock.unlock();
                    System.out.println("쓰기 락 해제: " + Thread.currentThread().getName());

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } finally {
                    readLock.unlock();
                    System.out.println("다운그레이드: 읽기 락 해제: " + Thread.currentThread().getName());
                }
            }).start();
        }

        // 여러 개의 스레드가 읽기 락을 사용하는 경우
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                readLock.lock();
                try {
                    System.out.println("읽기 락 획득: " + Thread.currentThread().getName());
                    int data = sharedData.getData();
                    System.out.println("데이터 읽기: " + data);
                } finally {
                    readLock.unlock();
                    System.out.println("읽기 락 해제: " + Thread.currentThread().getName());
                }
            }).start();
        }
    }
    static class SharedData {
        private int data = 0;

        public int getData() {
            return data;
        }
        public void setData(int value) {
            data = value;
        }
    }
}
