package io.concurrency.chapter08.exam03;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockWriteLockExample {
    public static void main(String[] args) throws InterruptedException {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        BankAccount account = new BankAccount(lock);

        // 읽기 스레드가 잔액 조회
        new Thread(() -> {
            int balance = account.getBalance();
            System.out.println("현재 잔액: " + balance);
        }).start();


        // 여러 스레드가 출금
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                int withdrawAmount = (int) (Math.random() * 1000);
                account.withdraw(withdrawAmount);
                System.out.println("출금: " + withdrawAmount);
            }).start();
        }

        // 읽기 스레드가 잔액 조회
        new Thread(() -> {
            int balance = account.getBalance();
            System.out.println("현재 잔액: " + balance);
        }).start();

    }

    static class BankAccount {
        private Map<String, Integer> balances;
        private ReadWriteLock lock;

        public BankAccount(ReadWriteLock lock) {
            this.lock = lock;
            this.balances = new HashMap<>();
            balances.put("account1", 10000); // 초기 잔액 1000 설정
        }

        public int getBalance() {
            lock.readLock().lock();
            try {
                return balances.get("account1");
            } finally {
                lock.readLock().unlock();
            }
        }

        public void withdraw(int amount) {
            lock.writeLock().lock();
            try {
                int currentBalance = balances.get("account1");
                if (currentBalance >= amount) {
                    currentBalance -= amount;
                    balances.put("account1", currentBalance);
                    System.out.println("출금 성공: " + amount);

                } else {
                    System.out.println("출금 실패: 잔액 부족");
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
}