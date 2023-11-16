package io.concurrency.chapter02.exam02;

public class ThreadStackExample {

    public static void main(String[] args) {
        // 스레드를 3개 생성하고 시작
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new MyRunnable(i));
            thread.start();
        }
    }

    private static class MyRunnable implements Runnable {
        private int threadId;

        public MyRunnable(int threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {
            firstMethod(threadId);
        }

        private void firstMethod(int id) {
            int localValue = id + 100; // 지역 변수
            secondMethod(localValue);
        }

        private void secondMethod(int value) {
            String localVar = "스레드 ID: " + threadId + ", Value: " + value;
            System.out.println(localVar);
        }
    }

}
