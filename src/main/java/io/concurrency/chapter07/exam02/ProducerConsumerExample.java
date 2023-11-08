package io.concurrency.chapter07.exam02;

import java.util.LinkedList;
import java.util.Queue;

class SharedQueue {
    private Queue<Integer> queue = new LinkedList<>();
    private final int CAPACITY = 5;  // 큐의 최대 용량
    private final Object lock = new Object();

    // 아이템을 큐에 추가
    public void produce(int item) throws InterruptedException {
        synchronized (lock) {
            while (queue.size() == CAPACITY) {  // 큐가 가득 찼으면
                System.out.println("큐가 가득 찼습니다. 생산 중지...");
                lock.wait();  // 대기
            }

            queue.add(item);
            System.out.println("생산: " + item);

            lock.notifyAll();  // 대기 중인 모든 소비자에게 알림
        }
    }

    // 아이템을 큐에서 제거
    public void consume() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {  // 큐가 비어 있으면
                System.out.println("큐가 비었습니다. 소비 중지...");
                lock.wait();  // 대기
            }

            int item = queue.poll();
            System.out.println("소비: " + item);

            lock.notifyAll();  // 대기 중인 모든 생산자에게 알림
        }
    }
}

public class ProducerConsumerExample {

    public static void main(String[] args) {
        SharedQueue sharedQueue = new SharedQueue();

        // 생산자 스레드
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    sharedQueue.produce(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"생산자");



        // 소비자 스레드
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    sharedQueue.consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"소비자");



        producer.start();
        consumer.start();
    }
}
