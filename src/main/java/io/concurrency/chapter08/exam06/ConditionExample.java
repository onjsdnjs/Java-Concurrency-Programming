package io.concurrency.chapter08.exam06;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {

    public static void main(String[] args) throws InterruptedException {

        ConditionExample conditionExample = new ConditionExample();
        Thread thread1 = new Thread(() -> {
            try {
                conditionExample.waitForFlag();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(conditionExample::setFlag);

        thread1.start();
        Thread.sleep(2000); // 스레드 thread1이 플래그를 기다리게 하기 위한 잠깐의 지연
        thread2.start();

        thread1.join();
        thread2.join();
    }
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean flag = false;

    public void waitForFlag() throws InterruptedException {
        lock.lock();
        try {
            while (!flag) {
                condition.await();
            }
            System.out.println("플래그가 활성화됨");
        } finally {
            lock.unlock();
        }
    }

    public void setFlag() {
        lock.lock();
        try {
            flag = true;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
