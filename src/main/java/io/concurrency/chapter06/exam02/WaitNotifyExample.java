package io.concurrency.chapter06.exam02;

public class WaitNotifyExample {

    private static final Object lock = new Object();
    private static boolean itemAvailable = false;

    public static void main(String[] args) {

        Thread producer = new Thread(() -> {
            synchronized (lock) {
                System.out.println("생산자가 아이템을 생성 중...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                itemAvailable = true;

                lock.notify();  // 아이템이 준비되었음을 알리기 위해 notify 호출
            }
        });

        Thread consumer = new Thread(() -> {
            synchronized (lock) {
                while (!itemAvailable) {
                    try {
                        System.out.println("소비자가 아이템을 기다리는 중...");
                        lock.wait();  // 아이템이 준비될 때까지 대기
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("소비자가 아이템을 소비함!");
            }
        });

        consumer.start();
        producer.start();
    }
}
