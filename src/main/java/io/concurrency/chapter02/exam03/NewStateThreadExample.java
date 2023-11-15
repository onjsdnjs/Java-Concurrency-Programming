package io.concurrency.chapter02.exam03;

public class NewStateThreadExample {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("스레드 실행 중");
        });
        System.out.println("스레드 상태: " + thread.getState()); // NEW
    }

}
