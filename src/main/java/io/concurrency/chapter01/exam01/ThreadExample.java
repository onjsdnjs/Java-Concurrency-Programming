package io.concurrency.chapter01.exam01;

public class ThreadExample {
    public static void main(String[] args) {

        // 두 개의 스레드 생성 및 공유 자원 공유
        SharedResource sharedResource = new SharedResource();
        Thread thread1 = createThread(sharedResource);
        Thread thread2 = createThread(sharedResource);

        thread1.start();
        thread2.start();

        try {
            // 스레드가 모두 종료될 때까지 대기
            thread1.join();
            thread2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 공유 자원 값 출력
        System.out.println("공유 자원 값: " + sharedResource.getSharedValue());
        System.out.println("메인 프로세스 종료.");
    }

    private static Thread createThread(SharedResource sharedResource) {
        return new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                sharedResource.increment();
            }
        });
    }
}

class SharedResource {
    private int sharedValue = 0;

    public int getSharedValue() {
        return sharedValue;
    }
    public synchronized void increment() {
        sharedValue++;
    }
}

