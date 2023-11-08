package io.concurrency.chapter08.exam03;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

public class BankAccount {
    private final ReadWriteLock lock;
    private Map<String, Integer> balance;

    public BankAccount(ReadWriteLock lock, int amount) {

        this.lock = lock;
        balance = new HashMap<>();
        balance.put("account1", amount);
    }

    public int getBalance(){
        lock.readLock().lock();
        try{
            Thread.sleep(1000);
            return balance.get("account1");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void deposit(int amount){
        lock.writeLock().lock();
        try{
            Thread.sleep(2000);
            int currentBalance = balance.get("account1");
            currentBalance += amount;
            balance.put("account1", currentBalance);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void withdraw(int amount){
        lock.writeLock().lock();
        try{
            int currentBalance = balance.get("account1");
            if(currentBalance >= amount){
                currentBalance -= amount;
                balance.put("account1", currentBalance);
                System.out.println(Thread.currentThread().getName() + " - 출금 성공");
            }else{
                System.out.println(Thread.currentThread().getName() + " - 출금 실패, 잔액부족");
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }finally {
            lock.writeLock().unlock();
        }
    }

}
