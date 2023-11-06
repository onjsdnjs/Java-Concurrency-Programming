package io.concurrency.chapter06.exam01;

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
