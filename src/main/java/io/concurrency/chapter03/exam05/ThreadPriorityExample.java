package io.concurrency.chapter03.exam05;

public class ThreadPriorityExample {

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                System.out.println("우선순위 1의 스레드 실행");
            }
        });
        thread1.setPriority(Thread.MIN_PRIORITY);

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                System.out.println("우선순위 3의 스레드 실행");
            }
        });
        thread3.setPriority(3);

        Thread thread7 = new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                System.out.println("우선순위 7의 스레드 실행");
            }
        });
        thread7.setPriority(7);

        Thread thread10 = new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                System.out.println("우선순위 10의 스레드 실행");
            }
        });
        thread10.setPriority(Thread.MAX_PRIORITY);

        thread1.start();
        thread3.start();
        thread7.start();
        thread10.start();
    }
}
