package io.concurrency.chapter04.exam05;

public class InheritableThreadLocalExample {
    public static InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {

        inheritableThreadLocal.set("부모 스레드의 값");

        Thread childThread = new Thread(() -> {
            // 부모 스레드로부터 값 상속
            System.out.println("자식 스레드에서 상속받은 값: " + inheritableThreadLocal.get());

            // 자식 스레드에서 값을 변경
            inheritableThreadLocal.set("자식 스레드의 새로운 값");
            System.out.println("자식 스레드에서 설정한 후의 값: " + inheritableThreadLocal.get());
        });

        childThread.start();

        // 자식 스레드가 종료될 것을 기다립니다.
        try {
            childThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 부모 스레드의 값 확인
        System.out.println("부모 스레드의 값: " + inheritableThreadLocal.get());
    }
}
