package io.concurrency.chapter02.exam01;

public class ImplementRunnableExample {
    public static void main(String[] args) {

        MyRunnable task = new MyRunnable();
        Thread thread = new Thread(task);
        thread.start();
    }
}

class MyRunnable implements Runnable {
    public void run() {
        System.out.println("스레드 실행 중");
    }
}
