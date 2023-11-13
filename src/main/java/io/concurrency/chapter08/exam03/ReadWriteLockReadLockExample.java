package io.concurrency.chapter08.exam03;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockReadLockExample {

    public static void main(String[] args) {

        ReadWriteLock lock = new ReentrantReadWriteLock();



        BankAccount account = new BankAccount(lock, 0);

        // 읽기 스레드가 잔액 조회
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                int balance = account.getBalance();
                System.out.println(Thread.currentThread().getName() + " - 현재 잔액: " + balance);
            }).start();
        }

        // 쓰기 스레드가 입금
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                int depositAmount = (int)(Math.random() * 1000);
                account.deposit(depositAmount);
                System.out.println(Thread.currentThread().getName() + " - 입금: " + depositAmount);
            }).start();
        }

        // 읽기 스레드가 잔액 조회
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                int balance = account.getBalance();
                System.out.println(Thread.currentThread().getName() + " - 현재 잔액: " + balance);
            }).start();
        }
    }
}
