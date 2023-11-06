package io.concurrency.chapter06.exam02;

public class BinarySemaphoreExample {

    public static void main(String[] args) {

        BinarySemaphore semaphore = new BinarySemaphore();
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


