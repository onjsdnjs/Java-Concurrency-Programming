package io.concurrency.chapter04.exam02;

public class IsInterruptedThreadStopExample {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                // 스레드의 작업을 수행합니다.
                System.out.println("작업 스레드가 실행 중입니다.");
            }
            System.out.println("인트럽트 상태: " + Thread.currentThread().isInterrupted());
            System.out.println("작업 스레드가 중단되었습니다.");
        });

        Thread stopper = new Thread(() -> {
            try {
                // 1초 후에 스레드를 중지합니다.
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            worker.interrupt();
            System.out.println("중단 스레드가 작업 스레드를 중단시켰습니다.");
        });

        worker.start();
        stopper.start();

        worker.join();
        stopper.join();
    }
}


