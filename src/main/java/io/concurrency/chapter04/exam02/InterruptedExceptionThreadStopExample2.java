package io.concurrency.chapter04.exam02;

public class InterruptedExceptionThreadStopExample2 {
    public static void main(String[] args) {
        Thread worker = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    // 스레드의 작업을 수행합니다.
                    System.out.println("작업 스레드가 실행 중입니다.");
                    System.out.println("인트럽트 상태 1 : " + Thread.currentThread().isInterrupted());
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                // sleep 메서드가 인터럽트되면 InterruptedException을 던지며
                // 이 때 interrupt 상태는 초기화 된다.
                // 그렇기 때문에 다시 interrupt 를 호출해 줘야 한다.
                System.out.println("인트럽트 상태 2 : " + Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
            }
            System.out.println("작업 스레드가 중단되었습니다.");
            System.out.println("인트럽트 상태 3 : " + Thread.currentThread().isInterrupted());
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
    }
}


