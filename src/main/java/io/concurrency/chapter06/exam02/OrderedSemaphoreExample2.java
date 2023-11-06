package io.concurrency.chapter06.exam02;

class OrderedSemaphore {
    private int signal = 0;  // 순서를 관리하는 신호 변수

    public synchronized void acquired(int order) {
        while (this.signal != order) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void release() {
        this.signal++;
        notifyAll();
    }
}

public class OrderedSemaphoreExample2 {
    public static void main(String[] args) throws InterruptedException {
        OrderedSemaphore semaphore = new OrderedSemaphore();

        Thread B = new Thread(() -> {
            semaphore.acquired(1);
            System.out.println("Thread B is running");
            semaphore.release();
        });

        Thread.sleep(1000);

        Thread A = new Thread(() -> {
            semaphore.acquired(0);
            System.out.println("Thread A is running");
            semaphore.release();
        });


        Thread C = new Thread(() -> {
            semaphore.acquired(2);
            System.out.println("Thread C is running");
            semaphore.release();
        });

        // 무작위 순서로 스레드 시작
        C.start();
        A.start();
        B.start();
    }
}
