package io.concurrency.chapter07.exam01.method;

public class InstanceMethodSynchronizedExamples2 {
    private int count = 0;

    public synchronized void increment() { // this 가 모니터가 된다
        count++;
        System.out.println(Thread.currentThread().getName() + "가 증가시켰습니다. 현재 값: " + count);
    }

    public synchronized void decrement() { // this 가 모니터가 된다
        count--;
        System.out.println(Thread.currentThread().getName() + "가 감소시켰습니다. 현재 값: " + count);
    }

    public int getValue() {
        return count;
    }

    public static void main(String[] args) {
        InstanceMethodSynchronizedExamples2 counter1 = new InstanceMethodSynchronizedExamples2();
        InstanceMethodSynchronizedExamples2 counter2 = new InstanceMethodSynchronizedExamples2();

        // 스레드가 동기화된 메서드를 동시에 호출하려고 시도
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter1.increment();
            }
        }, "스레드1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter2.decrement();
            }
        }, "스레드2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("최종 값: " + counter1.getValue());
        System.out.println("최종 값: " + counter2.getValue());
    }
}
