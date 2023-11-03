package io.concurrency.chapter03.exam01;

public class InterruptSleepExample {
    public static void main(String[] args) {

        Thread sleepingThread = new Thread(() -> {
            try {
                System.out.println("20초 동안 잠듭니다. 인터럽트되지 않는다면 계속 잠들어 있을 것입니다.");
                Thread.sleep(20000); // 스레드는 지정된 시간 동안 잠듭니다
                System.out.println("인터럽트 없이 잠에서 깨었습니다.");
            } catch (InterruptedException e) {
                System.out.println("잠들어 있는 동안 인터럽트 되었습니다!");
            }
        });

        sleepingThread.start();

        try {
            // 메인 스레드는 1초 동안 잠든 후 잠든 스레드를 인터럽트 합니다.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sleepingThread.interrupt();
    }
}
