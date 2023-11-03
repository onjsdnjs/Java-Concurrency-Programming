package io.concurrency.chapter05.exam02;

class Mutex {
    private boolean lock = false;

    public synchronized void acquired() {
        while (lock) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.lock = true;
    }

    public synchronized void release() {
        this.lock = false;
        this.notify();
    }
}

class SharedData {
    private int sharedValue = 0;
    private Mutex mutex;

    public SharedData(Mutex mutex) {
        this.mutex = mutex;
    }

    public void sum() {
        try {
            mutex.acquired(); // Lock 을 획득

            for (int i = 0; i < 10_000_0000; i++) {
                sharedValue++;
            }

        } finally {
            mutex.release(); // Lock 해제
        }
    }

    public int getSum() {
        return sharedValue;
    }
}

public class MutexExample {

    public static void main(String[] args) throws InterruptedException {
        SharedData sharedData = new SharedData(new Mutex());

        Thread th1 = new Thread(sharedData::sum);
        Thread th2 = new Thread(sharedData::sum);

        th1.start();
        th2.start();

        th1.join();
        th2.join();

        System.out.println("최종 합계: " + sharedData.getSum());
    }
}
