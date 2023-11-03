package io.concurrency.chapter05.exam03;

import java.util.Random;
import java.util.concurrent.Semaphore;

class Worker extends Thread {
    private final Semaphore semaphore;
    private Random random = new Random();

    public Worker(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(random.nextInt(3) * 1000);
            System.out.println(Thread.currentThread().getName() + ": availablePermits: " + semaphore.availablePermits());
            semaphore.acquire();
            Thread.sleep(random.nextInt(3) * 1000);
            System.out.println(Thread.currentThread().getName() + ": 작업 시작");
            semaphore.release();
            System.out.println(Thread.currentThread().getName() + ": 작업 종료, availablePermits: " + semaphore.availablePermits());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PermitCountThread extends Thread {
    private final Semaphore semaphore;

    public PermitCountThread(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            int currentPermits = semaphore.availablePermits();
            if (currentPermits == 5) {
                System.out.println("허용 개수 늘림");
                semaphore.release(3); // 허용 개수를 늘림

            } else {
                System.out.println("허용 개수 줄임");
                semaphore.acquire(3); // 허용 개수를 줄임

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class DynamicSemaphoreExample {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5); // 초기 허용 개수는 1개

        PermitCountThread thread = new PermitCountThread(semaphore);
        thread.start();

        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker(semaphore);
            worker.start();
        }
    }
}
