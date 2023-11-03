package io.concurrency.chapter02.exam01;

public class BasicSleepExample {
    public static void main(String[] args) {
        try {
            System.out.println("3초 후에 메시지가 출력됩니다.");
            Thread.sleep(3000);
            System.out.println("Hello, World!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
