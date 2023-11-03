package io.concurrency.chapter04.exam03;

public class UserThreadLifecycleExample {
    public static void main(String[] args) throws InterruptedException {
        // 사용자 스레드 1 생성
        Thread userThread1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("사용자 스레드 1 실행 중...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("사용자 스레드 1 종료.");
        });

        // 사용자 스레드 2 생성
        Thread userThread2 = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                System.out.println("사용자 스레드 2 실행 중...");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("사용자 스레드 2 종료.");
        });

        userThread1.start();
        userThread2.start();

        // 메인 스레드가 userThread1과 userThread2의 종료를 기다립니다.
//        userThread1.join();
//        userThread2.join();

        System.out.println("모든 사용자 스레드가 종료되었습니다. 메인 스레드 종료.");
    }
}
