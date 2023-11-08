package io.concurrency.chapter08.exam06;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean flag = false;

    public void awaiting() throws InterruptedException {
        lock.lock();
        try {
            while (!flag) {
                System.out.println("조건이 만족 하지 못해 대기함");
                condition.await();
            }
            System.out.println("임계영역 수행");
        } finally {
            lock.unlock();
        }
    }

    public void signaling() {
        lock.lock();
        try {
            flag = true;
            System.out.println("조건을 만족 시키고 깨움");
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ConditionExample conditionExample = new ConditionExample();

        Thread thread1 = new Thread(() -> {
            try {
                conditionExample.awaiting();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(conditionExample::signaling);

        thread1.start();
        Thread.sleep(2000); // 스레드 thread1이 플래그를 기다리게 하기 위한 잠깐의 지연
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
