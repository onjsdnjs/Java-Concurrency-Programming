package io.concurrency.chapter02.exam02;

public class MultiThreadAppTerminatedExample {
    public static void main(String[] args) {
        System.out.println("프로그램 시작");

        // 멀티스레드 실행
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new MyRunnable(i));
            thread.start();
        }

        System.out.println("메인 스레드 종료");
    }

    private static class MyRunnable implements Runnable {
        private int threadId;

        public MyRunnable(int threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println("스레드 " + threadId + " 작업 진행: " + i);
            }
            System.out.println("스레드 " + threadId + " 종료");
        }
    }
}
