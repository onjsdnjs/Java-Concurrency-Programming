package io.concurrency.chapter08.exam03;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultipleConditionsExample {
    private final Lock lock = new ReentrantLock();
    private final Condition condition1 = lock.newCondition();
    private final Condition condition2 = lock.newCondition();
    private String data1 = null;
    private String data2 = null;

    // data1에 데이터 추가
    public void addData1(String data) throws InterruptedException {
        Thread.sleep(1000);  // 2초 대기
        lock.lock();
        try {
            data1 = data;
            condition1.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // data2에 데이터 추가
    public void addData2(String data) throws InterruptedException {
        Thread.sleep(1000);  // 2초 대기
        lock.lock();
        try {
            data2 = data;
            condition2.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // data1의 데이터를 가져옴
    public String getData1() throws InterruptedException {
        lock.lock();
        try {
            while (data1 == null) {
                condition1.await();
            }
            String result = data1;
            data1 = null;  // 데이터를 소비하는 것을 시뮬레이션
            return result;
        } finally {
            lock.unlock();
        }
    }

    // data2의 데이터를 가져옴
    public String getData2() throws InterruptedException {
        lock.lock();
        try {
            while (data2 == null) {
                condition2.await();
            }
            String result = data2;
            data2 = null;  // 데이터를 소비하는 것을 시뮬레이션
            return result;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MultipleConditionsExample example = new MultipleConditionsExample();

        // 데이터를 가져오는 스레드
        new Thread(() -> {
            try {
                System.out.println("Data1: " + example.getData1());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("Data2: " + example.getData2());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 잠시 후 데이터를 추가하는 스레드 실행
        Thread.sleep(1000); // 1초 대기 후 데이터 추가 시작

        new Thread(() -> {
            try {
                example.addData1("data1_content");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                example.addData2("data2_content");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
