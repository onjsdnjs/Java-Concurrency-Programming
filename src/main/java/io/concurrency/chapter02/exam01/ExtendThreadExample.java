package io.concurrency.chapter02.exam01;

public class ExtendThreadExample {
        public static void main(String[] args) {

            MyThread thread = new MyThread();
            thread.start();

//            new MyThread().start();
        }
    }
class MyThread extends Thread {
    public void run() {
        System.out.println("스레드 확장에 의한 실행 중");
    }
}