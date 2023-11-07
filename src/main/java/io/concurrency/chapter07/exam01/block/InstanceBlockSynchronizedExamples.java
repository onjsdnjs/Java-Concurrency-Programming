package io.concurrency.chapter07.exam01.block;

public class InstanceBlockSynchronizedExamples {
    private int count = 0;

    // 별도의 객체를 생성하여 모니터로 사용
    private final Object lockObject = new Object();


    // 특정 객체의 블록에 synchronized 키워드를 사용하는 방법
    public void incrementBlockThis() {
        synchronized (this) { // this 가 모니터가 된다
            count++;
            System.out.println(Thread.currentThread().getName() + " - 블록 동기화로 증가: " + count);
        }
    }

    // 별도의 객체를 사용하여 동기화하는 방법
    public void incrementBlockKLockObject() {
        synchronized (lockObject) { // Object 가 모니터가 된다
            count++;
            System.out.println(Thread.currentThread().getName() + " - 별도 객체 동기화로 증가: " + count);
        }
    }

    public static void main(String[] args) {
        InstanceBlockSynchronizedExamples example = new InstanceBlockSynchronizedExamples();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementBlockThis();
            }
        }, "스레드1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementBlockThis();
            }
        }, "스레드2");

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementBlockKLockObject();
            }
        }, "스레드3");

        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementBlockKLockObject();
            }
        }, "스레드4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            System.out.println("count: " + example.count);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
