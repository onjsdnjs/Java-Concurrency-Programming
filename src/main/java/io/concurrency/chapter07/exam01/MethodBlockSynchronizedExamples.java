package io.concurrency.chapter07.exam01;


class MethodBlock{}
public class MethodBlockSynchronizedExamples {
    private static int count = 0;

    // 별도의 객체를 생성하여 모니터로 사용
    private final Object lockObject = new Object();

    public synchronized void incrementInstanceMethod() {// this 가 모니터가 된다
        count++;
        System.out.println(Thread.currentThread().getName() + " - 메서드 동기화로 증가: " + count);
    }

    public static synchronized void incrementStaticMethod() {// MethodBlockSynchronizedExamples 가 모니터가 된다
        count++;
        System.out.println(Thread.currentThread().getName() + " - 메서드 동기화로 증가: " + count);
    }

    // 특정 객체의 블록에 synchronized 키워드를 사용하는 방법
    public void incrementInstanceBlockThis() {
        synchronized (this) { // this 가 모니터가 된다
            count++;
            System.out.println(Thread.currentThread().getName() + " - 블록 동기화로 증가: " + count);
        }
    }

    // 별도의 객체를 사용하여 동기화하는 방법
    public void incrementInstanceBlockLockObject() {
        synchronized (lockObject) { // Object 가 모니터가 된다
            count++;
            System.out.println(Thread.currentThread().getName() + " - 별도 객체 동기화로 증가: " + count);
        }
    }

    // 별도의 클래스를 사용하여 동기화하는 방법
    public static void incrementStaticBlockClass() {
        synchronized (MethodBlockSynchronizedExamples.class) { // MethodBlockSynchronizedExamples 가 모니터가 된다
            count++;
            System.out.println(Thread.currentThread().getName() + " - 별도 클래스 동기화로 증가: " + count);
        }
    }

    // 별도의 클래스를 사용하여 동기화하는 방법
    public static void incrementStaticBlockOtherClass() {
        synchronized (MethodBlock.class) { // MethodBlock 가 모니터가 된다
            count++;
            System.out.println(Thread.currentThread().getName() + " - 별도 클래스 동기화로 증가: " + count);
        }
    }



    public static void main(String[] args) {
        MethodBlockSynchronizedExamples example = new MethodBlockSynchronizedExamples();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementInstanceMethod();
            }
        }, "스레드1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementInstanceBlockThis();
            }
        }, "스레드2");

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementStaticBlockClass();
            }
        }, "스레드3");

        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementInstanceBlockLockObject();
            }
        }, "스레드4");

        Thread t5 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementStaticMethod();
            }
        }, "스레드5");

        Thread t6 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementStaticBlockOtherClass();
            }
        }, "스레드6");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
            System.out.println("example.count: " + example.count);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
