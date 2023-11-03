package io.concurrency.chapter03.exam01;

public class MultiThreadSleepExample {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            try {
                System.out.println("스레드 1은 5초 동안 잠듭니다");
                Thread.sleep(5000);
                System.out.println("스레드 1이 깨어났습니다");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                System.out.println("스레드 2는 2초 동안 잠듭니다");
                Thread.sleep(2000);
                System.out.println("스레드 2가 깨어났습니다");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
    }
}
