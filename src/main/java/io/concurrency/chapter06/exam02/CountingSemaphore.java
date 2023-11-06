package io.concurrency.chapter06.exam02;

public class CountingSemaphore implements CommonSemaphore {
    private int signal;
    private int permits;

    public CountingSemaphore(int permits) {
        this.permits = permits;
        this.signal = permits;
    }

    public void acquired() {
        synchronized (this) {
            while (this.signal == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.signal--;
        }
        System.out.println(Thread.currentThread().getName() + " 락 획득, 현재 세마포어 값: " + signal);
    }

    public synchronized void release() {
        if (this.signal < permits) { // signal 값이 permits 보다 작을 때만 증가
            this.signal++;
            System.out.println(Thread.currentThread().getName() + " 락 해제, 현재 세마포어 값: " + signal);
            notifyAll();
        }
    }
}
