package io.concurrency.chapter08.exam02;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDowngradeExample {
    private int data;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void writeAndReadData() {
        System.out.println("쓰기 잠금 획득 시도...");
        lock.writeLock().lock();
        System.out.println("쓰기 잠금 획득!");

        try {
            // 쓰기 작업 수행
            System.out.println("데이터 수정 중...");
            data = 42;

            // 다운그레이드: 쓰기 잠금이 활성화된 상태에서 읽기 잠금을 활성화
            System.out.println("다운그레이드를 위해 읽기 잠금 획득 시도...");
            lock.readLock().lock();
            System.out.println("읽기 잠금 획득!");
        } finally {
            // 쓰기 잠금을 해제
            System.out.println("쓰기 잠금 해제 중...");
            lock.writeLock().unlock();
            System.out.println("쓰기 잠금 해제!");
        }

        try {
            // 안전하게 데이터 읽기
            System.out.println("데이터 읽는 중...");
            System.out.println(data);
        } finally {
            // 읽기 잠금 해제
            System.out.println("읽기 잠금 해제 중...");
            lock.readLock().unlock();
            System.out.println("읽기 잠금 해제!");
        }
    }

    public static void main(String[] args) {
        ReentrantReadWriteLockDowngradeExample example = new ReentrantReadWriteLockDowngradeExample();
        example.writeAndReadData();
    }

}
