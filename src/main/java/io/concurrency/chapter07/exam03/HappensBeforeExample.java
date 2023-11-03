package io.concurrency.chapter07.exam03;

public class HappensBeforeExample {
    private int x = 0;
//    private volatile boolean flag = false;
    private boolean flag = false;

    public void writer() {
        x = 42;
        flag = true;
    }

    public void reader() {
        if (flag) {
            System.out.println("result: " + x);
        }
    }

    public static void main(String[] args) {
        HappensBeforeExample example = new HappensBeforeExample();

        Thread writerThread = new Thread(() -> {
            example.writer();
        });

        Thread readerThread = new Thread(() -> {
            example.reader();
        });

        writerThread.start();
        readerThread.start();

        try {
            writerThread.join();
            readerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
