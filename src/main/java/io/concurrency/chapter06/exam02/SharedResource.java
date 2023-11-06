package io.concurrency.chapter06.exam02;

public class SharedResource {
    private int value = 0;
    private CommonSemaphore commonSemaphore;

    public SharedResource(CommonSemaphore commonSemaphore) {
        this.commonSemaphore = commonSemaphore;
    }

    public void increment() {
        commonSemaphore.acquired();
        for(int i=0; i< 1000000; i++) {
            value++;
        }
        commonSemaphore.release();
    }
    public int getValue() {
        return value;
    }
}
