package io.concurrency.chapter06.exam03;

public class CountingSemaphoreExample {
    public static void main(String[] args) {

        int permits = 10; // 최대 3개의 스레드가 동시에 작업을 수행할 수 있습니다.
        CountingCommonSemaphore semaphore = new CountingCommonSemaphore(permits);
        SharedResource resource = new SharedResource(semaphore);

        int threadCount = 5; // 전체 스레드 개수

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(resource::increment);
            threads[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("최종 값: " + resource.getValue());
    }

    static class SharedResource {
        private int value = 0;
        private CommonSemaphore commonSemaphore;

        public SharedResource(CommonSemaphore commonSemaphore) {
            this.commonSemaphore = commonSemaphore;
        }

        public void increment() {
            commonSemaphore.acquired();
            synchronized (this) {  // 카운팅 세마포어는 여러개의 스레드가 동시에 접근이 가능하므로 value에 대한 동기화를 해 주어야 한다
                for (int i = 0; i < 1000000; i++) {
                    value++;
                }
            }
            commonSemaphore.release();
        }
        public int getValue() {
            return value;
        }
    }
}

