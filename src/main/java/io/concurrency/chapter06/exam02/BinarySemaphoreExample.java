package io.concurrency.chapter06.exam02;

public class BinarySemaphoreExample {

    public static void main(String[] args) {

        BinaryCommonSemaphore semaphore = new BinaryCommonSemaphore();
        SharedResource resource = new SharedResource(semaphore);

        Thread t1 = new Thread(resource::increment);
        Thread t2 = new Thread(resource::increment);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("최종 값: " + resource.getValue());
    }
}

class SharedResource {
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


