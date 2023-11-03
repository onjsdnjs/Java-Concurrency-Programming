package io.concurrency.chapter08.exam03;

public class WaitNotifyExample {

    public static void main(String[] args) throws InterruptedException {
        // wait() / notify() 예제 실행
        WaitNotifyExample waitNotifyExample = new WaitNotifyExample();
        Thread thread1 = new Thread(() -> {
            try {
                waitNotifyExample.waitForFlag();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(waitNotifyExample::setFlag);

        thread1.start();
        Thread.sleep(1000); // 스레드 thread1이 플래그를 기다리게 하기 위한 잠깐의 지연
        thread2.start();

        thread1.join();
        thread2.join();

        // Condition의 await() / signal() 예제 실행
        ConditionExample conditionExample = new ConditionExample();
        Thread thread3 = new Thread(() -> {
            try {
                conditionExample.waitForFlag();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread4 = new Thread(conditionExample::setFlag);

        thread3.start();
        Thread.sleep(1000); // 스레드 thread3이 플래그를 기다리게 하기 위한 잠깐의 지연
        thread4.start();

        thread3.join();
        thread4.join();
    }
    private final Object monitor = new Object();
    private boolean flag = false;

    public void waitForFlag() throws InterruptedException {
        synchronized (monitor) {
            while (!flag) {
                monitor.wait();
            }
            System.out.println("플래그가 활성화됨");
        }
    }

    public void setFlag() {
        synchronized (monitor) {
            flag = true;
            monitor.notifyAll();
        }
    }
}
