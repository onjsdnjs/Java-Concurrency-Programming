package io.concurrency.chapter06.exam02;

public class OrderedSemaphoreExample {
    public static void main(String[] args) throws InterruptedException {
        CommonSemaphore commonSemaphore = new BinaryCommonSemaphore();

        WaitThread waitThread = new WaitThread(commonSemaphore);
        SignalThread signalThread = new SignalThread(commonSemaphore);

        waitThread.start();
        Thread.sleep(1000);
        signalThread.start();
    }

    static class BinaryCommonSemaphore implements CommonSemaphore {
        private int signal = 0;

        public synchronized void acquired() {
            while (this.signal == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.signal = 0;
        }

        public synchronized void release() {
            this.signal = 1;
            notify();
        }
    }
}

class WaitThread extends Thread {
    private CommonSemaphore commonSemaphore;

    public WaitThread(CommonSemaphore commonSemaphore) {
        this.commonSemaphore = commonSemaphore;
    }

    public void run() {
        this.commonSemaphore.acquired();
        // critical section
        System.out.println(Thread.currentThread().getName() + ": WaitThread");
    }
}

class SignalThread extends Thread {
    private CommonSemaphore commonSemaphore;

    public SignalThread(CommonSemaphore commonSemaphore) {
        this.commonSemaphore = commonSemaphore;
    }

    public void run() {
        // do task;
        System.out.println(Thread.currentThread().getName() + ": SignalThread");
        this.commonSemaphore.release();
    }
}
