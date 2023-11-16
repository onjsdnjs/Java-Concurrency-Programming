package io.concurrency.chapter02.exam01;

public class ExtendThreadExample {
        public static void main(String[] args) {

            MyThread thread1 = new MyThread();
            thread1.start();

            MyThread thread2 = new MyThread();
            thread2.start();

//            new MyThread().start();
        }
    }
class MyThread extends Thread {
    public void run() {
        System.out.println(Thread.currentThread().getName()+ " :스레드 실행 중");
    }
}