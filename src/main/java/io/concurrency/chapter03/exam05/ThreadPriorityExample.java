package io.concurrency.chapter03.exam05;

public class ThreadPriorityExample {

    public static void main(String[] args) throws InterruptedException {

        CountingThread maxThread = new CountingThread("우선 순위가 높은 스레드", Thread.MAX_PRIORITY);
        CountingThread normThread = new CountingThread("우선 순위가 기본인 스레드", Thread.NORM_PRIORITY);
        CountingThread minThread = new CountingThread("우선 순위가 낮은 스레드", Thread.MIN_PRIORITY);

        maxThread.start();
        normThread.start();
        minThread.start();

        maxThread.join();
        normThread.join();
        minThread.join();

        System.out.println("작업 완료");

    }

    static class CountingThread extends Thread {
        private final String threadName;
        private int count = 0;

        public CountingThread(String threadName, int priority) {
            this.threadName = threadName;
            setPriority(priority);
        }

        @Override
        public void run() {
            while (count < 10000000) {
                count++;
            }
            System.out.println(threadName + ": " + count);
        }
    }
}
