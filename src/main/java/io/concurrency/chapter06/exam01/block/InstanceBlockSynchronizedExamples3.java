package io.concurrency.chapter06.exam01.block;

public class InstanceBlockSynchronizedExamples3 {
    private int count = 0;

    // 별도의 객체를 생성하여 모니터로 사용
    private final Object lockObject = new Object();


    // 특정 객체의 블록에 synchronized 키워드를 사용하는 방법
    public void incrementBlockSync() {
        synchronized (this) { // this 가 모니터가 된다
            count++;
            System.out.println(Thread.currentThread().getName() + " - 블록 동기화로 증가: " + count);
        }
    }

    // 별도의 객체를 사용하여 동기화하는 방법
    public void incrementWithLockObject() {
        synchronized (lockObject) { // Object 가 모니터가 된다
            count++;
            System.out.println(Thread.currentThread().getName() + " - 별도 객체 동기화로 증가: " + count);
        }
    }

    public static void main(String[] args) {
        InstanceBlockSynchronizedExamples3 example1 = new InstanceBlockSynchronizedExamples3();
        InstanceBlockSynchronizedExamples3 example2 = new InstanceBlockSynchronizedExamples3();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 300000; i++) {
                example1.incrementBlockSync();
            }
        }, "스레드1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 300000; i++) {
                example2.incrementBlockSync();
            }
        }, "스레드2");

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 300000; i++) {
                example1.incrementWithLockObject();
            }
        }, "스레드3");

        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 300000; i++) {
                example2.incrementWithLockObject();
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
            System.out.println("example1.count: " + example1.count);
            System.out.println("example2.count: " + example2.count);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
