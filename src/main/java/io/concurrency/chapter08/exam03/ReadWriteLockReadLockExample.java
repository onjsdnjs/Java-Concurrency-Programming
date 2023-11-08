package io.concurrency.chapter08.exam03;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockReadLockExample {
    public static void main(String[] args) {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        BankAccount account = new BankAccount(lock);

        // 여러 스레드가 잔액 조회
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                int balance = account.getBalance();
                System.out.println(Thread.currentThread().getName() + " - 현재 잔액: " + balance);
            }).start();
        }

        // 여러 스레드가 입금
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                int depositAmount = (int) (Math.random() * 1000);
                account.deposit(depositAmount);
                System.out.println(Thread.currentThread().getName() + " - 입금: " + depositAmount);

            }).start();
        }

        // 여러 스레드가 잔액 조회
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                int balance = account.getBalance();
                System.out.println(Thread.currentThread().getName() + " - 현재 잔액: " + balance);
            }).start();
        }
    }
}

class BankAccount {
    private Map<String, Integer> balances;
    private ReadWriteLock lock;

    public BankAccount(ReadWriteLock lock) {
        this.lock = lock;
        this.balances = new HashMap<>();
        balances.put("account1", 0);
    }

    public int getBalance() {
        lock.readLock().lock();
        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return balances.get("account1");
        } finally {
            lock.readLock().unlock();
        }
    }

    public void deposit(int amount) {
        lock.writeLock().lock();
        try {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int currentBalance = balances.get("account1");
            currentBalance += amount;
            balances.put("account1", currentBalance);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
