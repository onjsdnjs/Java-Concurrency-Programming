package io.concurrency.chapter07.exam03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TimedAwaitExample {
    private final Queue<Integer> queue = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();

    public void produce() throws InterruptedException {
        Thread.sleep(3000);  // 3초 동안 대기
        lock.lock();
        try {
            queue.offer(1);
            System.out.println("데이터 추가");
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void consume() throws InterruptedException {
        lock.lock();
        try {
            if (!notEmpty.await(2, TimeUnit.SECONDS)) { // 2초 동안 대기
                System.out.println("2초 동안 데이터를 받지 못했습니다. 작업을 중단합니다.");
                return;
            }
            int value = queue.poll();
            System.out.println("소비: " + value + ", 큐 크기: " + queue.size());
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TimedAwaitExample example = new TimedAwaitExample();

        // 소비자 스레드
        Thread consumerThread = new Thread(() -> {
            try {
                example.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 생산자 스레드
        Thread producerThread = new Thread(() -> {
            try {
                example.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        consumerThread.start();
        producerThread.start();
    }
}
