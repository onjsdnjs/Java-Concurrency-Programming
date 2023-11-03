package io.concurrency.chapter03.exam02;

public class BasicJoinExample {

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            try {
                System.out.println("스레드가 3초 동안 작동합니다.");
                Thread.sleep(5000);
                System.out.println("스레드 작동 완료.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();

        System.out.println("메인 스레드가 다른 스레드의 완료를 기다립니다.");

        try {

            thread.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("메인 스레드가 계속 진행합니다");
    }
}
