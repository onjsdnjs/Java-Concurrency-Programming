package io.concurrency.chapter03.exam04;

public class ThreadNamingExample {
    public static void main(String[] args) throws InterruptedException {

        // 스레드 이름을 생성자에 전달하여 설정
        Thread myThread = new Thread(() -> {
            System.out.println("현재 스레드 이름: " + Thread.currentThread().getName());
        }, "myThread");
        myThread.start();

        // setName() 메서드를 사용하여 스레드 이름 설정
        Thread yourThread = new Thread(() -> {
            System.out.println("현재 스레드 이름: " + Thread.currentThread().getName());
        },"yourThread");
//        yourThread.setName("yourThread");
        yourThread.start();

        // 여러 개의 스레드를 생성하여 기본 스레드 이름을 출력
        for (int i = 0; i < 5; i++) {
            Thread defaultThread = new Thread(() -> {
                System.out.println("현재 스레드 이름: " + Thread.currentThread().getName());
            });
            defaultThread.start();
        }

        Thread.sleep(2000);
    }
}
