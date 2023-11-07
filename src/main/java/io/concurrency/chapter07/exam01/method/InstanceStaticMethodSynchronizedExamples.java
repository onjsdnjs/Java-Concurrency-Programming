package io.concurrency.chapter07.exam01.method;

public class InstanceStaticMethodSynchronizedExamples {

    private static int staticCount = 0;
    private int instanceCount = 0;

    public synchronized void incrementInstanceCount(){  // this 가 모니터가 된다
        instanceCount++;
        staticCount++;
        System.out.println(Thread.currentThread().getName() + " 가 인스턴스 카운터를 증가시켰습니다. 현재 값:" + instanceCount);
    }
    public static synchronized void incrementStaticCount(){ // InstanceStaticMethodSynchronizedExamples 가 모니터가 된다
        staticCount++;
        System.out.println(Thread.currentThread().getName() + " 가 장적 카운터를 증가시켰습니다. 현재 값:" + staticCount);
    }

    public static void main(String[] args) {
        InstanceStaticMethodSynchronizedExamples example = new InstanceStaticMethodSynchronizedExamples();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                example.incrementInstanceCount();
            }
        },"스레드 1");

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                example.incrementInstanceCount();
            }
        },"스레드 2");

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                InstanceStaticMethodSynchronizedExamples.incrementStaticCount();
            }
        },"스레드 3");

        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                InstanceStaticMethodSynchronizedExamples.incrementStaticCount();
            }
        },"스레드 4");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("최종값:"  + example.instanceCount);
        System.out.println("최종값:"  + staticCount);
    }
}
