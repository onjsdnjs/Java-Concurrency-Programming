package io.concurrency.chapter02.exam05;

public class ThreadPriorityAPIExample {
    public static void main(String[] args) {

        //기본 우선순위 확인
        Thread thread = new Thread();
        System.out.println("기본 우선순위: " + thread.getPriority());

        //최대, 최소, 기본 우선순위 확인
        System.out.println("최소 우선순위: " + Thread.MIN_PRIORITY);
        System.out.println("기본 우선순위: " + Thread.NORM_PRIORITY);
        System.out.println("최대 우선순위: " + Thread.MAX_PRIORITY);


        //스레드 우선순위 변경하기
        thread.setPriority(7);
        System.out.println("우선순위 변경 후: " + thread.getPriority());
        thread.start();

        Thread minPriorityThread = new Thread(() -> {
            System.out.println("최소 우선순위 스레드 실행, " + Thread.currentThread().getPriority());
        });
        minPriorityThread.setPriority(Thread.MIN_PRIORITY);

        Thread normPriorityThread = new Thread(() -> {
            System.out.println("보통 우선순위 스레드 실행, " + Thread.currentThread().getPriority());
        });
        normPriorityThread.setPriority(Thread.NORM_PRIORITY);

        Thread maxPriorityThread = new Thread(() -> {
            System.out.println("최대 우선순위 스레드 실행, " + Thread.currentThread().getPriority());
        });
        maxPriorityThread.setPriority(Thread.MAX_PRIORITY);

        minPriorityThread.start();
        normPriorityThread.start();
        maxPriorityThread.start();
    }
}
