package io.concurrency.chapter02.exam01;

public class AnonymousRunnableClassExample {
    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            public void run() {
                System.out.println("무명 Runnable 클래스에 의한 스레드 실행 중");
            }
        });
        thread.start();
    }
}
