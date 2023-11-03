package io.concurrency.chapter06.exam01.block;

class MethodBlock{}
public class StaticBlockSynchronizedExamples {
    private int count = 0;

    // 별도의 객체를 생성하여 모니터로 사용
    private final Object lockObject = new Object();


    // 특정 객체의 블록에 synchronized 키워드를 사용하는 방법
    public void incrementBlockSync() {
        synchronized (StaticBlockSynchronizedExamples.class) { // MethodBlockSynchronizedExamples 가 모니터가 된다
            count++;
            System.out.println(Thread.currentThread().getName() + " - 블록 동기화로 증가: " + count);
        }
    }

    // 별도의 클래스를 사용하여 동기화하는 방법
    public void incrementWithLockObject() {
        synchronized (MethodBlock.class) { // MethodBlock 가 모니터가 된다
            count++;
            System.out.println(Thread.currentThread().getName() + " - 별도 클래스 동기화로 증가: " + count);
        }
    }

    public static void main(String[] args) {
        StaticBlockSynchronizedExamples example = new StaticBlockSynchronizedExamples();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementBlockSync();
            }
        }, "스레드1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementWithLockObject();
            }
        }, "스레드2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            System.out.println("count: " + example.count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
