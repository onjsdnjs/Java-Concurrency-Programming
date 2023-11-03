package io.concurrency.chapter07.exam04;

public class NonDeadlockOrderExample {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            process1();
        });

        Thread thread2 = new Thread(() -> {
            process2();
        });

        thread1.start();
        thread2.start();
    }

    private static void process1() {
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + " 이 lock1 을 획득하였습니다.");

            try {
                // 스레드 간의 경쟁 조건을 만들기 위해 잠시 대기
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + " 이 lock2 을 획득하였습니다.");
            }
        }
    }

    private static void process2() {
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + " 이 lock2 을 획득하였습니다.");

            try {
                // 스레드 간의 경쟁 조건을 만들기 위해 잠시 대기
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + " 이 lock1 을 획득하였습니다.");
            }
        }
    }
}
