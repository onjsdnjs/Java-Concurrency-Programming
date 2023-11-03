package io.concurrency.chapter07.exam01.method;

public class InstanceStaticMethodSynchronizedExamples {
    private static int staticCounter = 0;
    private int instanceCounter = 0;

    public synchronized void incrementInstanceCounter() { // this 가 모니터가 된다
        instanceCounter++;
        System.out.println(Thread.currentThread().getName() + "가 인스턴스 카운터를 증가시켰습니다. 현재 값: " + instanceCounter);
    }

    public static synchronized void incrementStaticCounter() { // InstanceStaticMethodSynchronizedExamples 가 모니터가 된다
        staticCounter++;
        System.out.println(Thread.currentThread().getName() + "가 정적 카운터를 증가시켰습니다. 현재 값: " + staticCounter);
    }

    public static void main(String[] args) {
        InstanceStaticMethodSynchronizedExamples example1 = new InstanceStaticMethodSynchronizedExamples();
        InstanceStaticMethodSynchronizedExamples example2 = new InstanceStaticMethodSynchronizedExamples();

        // 인스턴스 메서드 동기화 테스트
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                example1.incrementInstanceCounter();
            }
        }, "스레드1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                example2.incrementInstanceCounter();
            }
        }, "스레드2");

        // 정적 메서드 동기화 테스트
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                InstanceStaticMethodSynchronizedExamples.incrementStaticCounter();
            }
        }, "스레드3");

        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                InstanceStaticMethodSynchronizedExamples.incrementStaticCounter();
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("최종 값: " + example1.instanceCounter);
        System.out.println("최종 값: " + example2.instanceCounter);
        System.out.println("최종 값: " + staticCounter);
    }
}
