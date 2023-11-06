package io.concurrency.chapter04.exam02;

public class FlagThreadStopExample {
    // volatile 키워드 추가
   volatile boolean running = true;
//    boolean running = true;

    public void volatileTest() {
        new Thread(() -> {
            int count = 0;
            while (running) {
                /*try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }*/
                count++;
            }
            System.out.println("Thread 1 종료. Count: " + count);
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            System.out.println("Thread 2 종료 중..");
            running = false;
        }).start();
    }

    public static void main(String[] args) {
        new FlagThreadStopExample().volatileTest();
    }
}
