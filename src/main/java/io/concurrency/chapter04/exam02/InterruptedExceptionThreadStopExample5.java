package io.concurrency.chapter04.exam02;

public class InterruptedExceptionThreadStopExample5 {
    public static void main(String[] args) {
        Thread worker = new Thread(() -> {
            try {
                while (true) {
                    // 스레드의 작업을 수행합니다.
                    System.out.println("작업 스레드가 실행 중입니다.");
                    System.out.println("인트럽트 상태 1 : " + Thread.currentThread().isInterrupted());

                    if(Thread.interrupted()) throw new InterruptedException("thread is interrupted");
                }
            } catch (InterruptedException e) {
                // Thread.interrupted() 는 interrupt 상태를 초기화 한다.
                // 그래서 필요시 자신에게 interrupt() 를 호출해 준다
                System.out.println("인트럽트 상태 2 : " + Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
            }
            System.out.println("작업 스레드가 중단되었습니다.");
            System.out.println("인트럽트 상태 3 : " + Thread.currentThread().isInterrupted());
        });

        Thread stopper = new Thread(() -> {
            try {
                // 0.1초 후에 스레드를 중지합니다.
                Thread.sleep(100);
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


