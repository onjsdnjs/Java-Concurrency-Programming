package io.concurrency.chapter10.exam01;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleThreadPool {

    private final int numThreads; // 스레드 풀 내 스레드 수
    private final Queue<Runnable> taskQueue; // 작업 큐
    private final Thread[] threads; // 스레드 배열
    private volatile boolean isShutdown; // 종료 여부 플래그

    public SimpleThreadPool(int numThreads) {
        this.numThreads = numThreads;
        this.taskQueue = new LinkedList<>(); // 작업 큐 초기화
        this.threads = new Thread[numThreads]; // 스레드 배열 초기화
        this.isShutdown = false; // 종료 여부 플래그 초기화

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new WorkerThread(); // 작업자 스레드 생성
            threads[i].start(); // 스레드 시작
        }
    }

    public void submit(Runnable task) {
        if (!isShutdown) {
            synchronized (taskQueue) {
                taskQueue.offer(task); // 작업 큐에 작업 추가
                taskQueue.notifyAll(); // 대기 중인 스레드에게 작업이 추가되었음을 알림
            }
        }
    }

    public void shutdown() {
        isShutdown = true; // 스레드 풀 종료 플래그 설정
        synchronized (taskQueue) {
            taskQueue.notifyAll(); // 대기 중인 모든 스레드를 깨워 종료하도록 함
        }
        // 스레드 종료까지 대기하는 부분 추가
        for (Thread thread : threads) {
            try {
                thread.join(); // 각 스레드가 종료될 때까지 대기
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private class WorkerThread extends Thread {
        public void run() {
            while (!isShutdown) {
                Runnable task;
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty() && !isShutdown) {
                        try {
                            taskQueue.wait(); // 작업이 있을 때까지 대기
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    if (!taskQueue.isEmpty()) {
                        task = taskQueue.poll(); // 작업 큐에서 작업 가져옴
                    } else {
                        continue; // 작업이 없으면 다시 대기
                    }
                }
                try {
                    task.run(); // 작업 실행
                } catch (Throwable t) {
                    // 예외 처리 로직 추가
                    t.printStackTrace();
                }
            }
        }
    }
}
