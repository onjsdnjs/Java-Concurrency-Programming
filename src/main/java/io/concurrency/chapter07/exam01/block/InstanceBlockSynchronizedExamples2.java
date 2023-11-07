package io.concurrency.chapter07.exam01.block;

public class InstanceBlockSynchronizedExamples2 {
    private int count1 = 0;
    private int count2 = 0;

    // 별도의 객체를 생성하여 모니터로 사용
    private final Object lockObject = new Object();


    // 특정 객체의 블록에 synchronized 키워드를 사용하는 방법
    public void incrementBlockThis() {
        synchronized (this) { // this 가 모니터가 된다
            count1++;
            System.out.println(Thread.currentThread().getName() + " - 블록 동기화로 증가: " + count1);
        }
    }

    // 별도의 객체를 사용하여 동기화하는 방법
    public void incrementBlockKLockObject() {
        synchronized (lockObject) { // Object 가 모니터가 된다
            count2++;
            System.out.println(Thread.currentThread().getName() + " - 별도 객체 동기화로 증가: " + count2);
        }
    }

    public static void main(String[] args) {
        InstanceBlockSynchronizedExamples2 example1 = new InstanceBlockSynchronizedExamples2();
        InstanceBlockSynchronizedExamples2 example2 = new InstanceBlockSynchronizedExamples2();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example1.incrementBlockThis();
            }
        }, "스레드1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example2.incrementBlockThis();
            }
        }, "스레드2");

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example1.incrementBlockKLockObject();
            }
        }, "스레드3");

        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example2.incrementBlockKLockObject();
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
            System.out.println("example1.count1: " + example1.count1);
            System.out.println("example1.count2: " + example1.count2);
            System.out.println("example2.count1: " + example2.count1);
            System.out.println("example2.count2: " + example2.count2);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
