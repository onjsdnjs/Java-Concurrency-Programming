package io.concurrency.chapter02.exam03;

public class ThreadLifecycleExample {

    public static void main(String[] args) throws InterruptedException {

        /**
         * NEW 상태의 스레드 생성
         */
        Thread newThread = new Thread(() -> {});
        System.out.println("스레드 생성: " + newThread.getState());

        /**
         * RUNNABLE 상태의 스레드 생성
         */
        Thread runnableThread = new Thread(() -> {
            while (true) {
                // 무한 루프
            }
        });
        runnableThread.start();

        /**
         * TIMED_WAITING 상태의 스레드 생성
         */
        Thread timedWaitingThread = new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        timedWaitingThread.start();

        /**
         * WAITING 상태의 스레드 생성
         */
        final Object lock = new Object();
        Thread waitingThread = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        waitingThread.start();
        Thread.sleep(100); // 스레드 상태 변화를 위한 대기 시간

        /**
         * BLOCKED 상태의 스레드 생성
         */
        Thread runningThread = new Thread(() -> {
            synchronized (lock) {
                while (true) {
                    // 무한 루프로 lock 을 계속 점유
                }
            }
        });
        runningThread.start();
        Thread.sleep(100); // runningThread 가 lock을 점유하도록 잠시 대기

        Thread blockedThread = new Thread(() -> {
            synchronized (lock) {
            }
        });
        blockedThread.start();
        Thread.sleep(100); // blockedThread 가 lock을 기다리는 상태로 대기

        /**
         * TERMINATED 상태
         */
        newThread.start();
        newThread.join();

        System.out.println("스레드 실행: " + runnableThread.getState());
        System.out.println("스레드 지정된 시간 대기: " + timedWaitingThread.getState());
        System.out.println("스레드 대기: " + waitingThread.getState());
        System.out.println("스레드 차단: " + blockedThread.getState());
        System.out.println("스레드 종료: " + newThread.getState());
    }
}
