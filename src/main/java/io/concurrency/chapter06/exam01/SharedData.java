package io.concurrency.chapter06.exam01;
public class SharedData {
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