package io.concurrency.chapter04.exam04;

public class ThreadGroupExample {

    public static void main(String[] args) {
        // 메인 스레드 그룹 가져오기
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();

        // 새로운 스레드 그룹 생성
        ThreadGroup customGroup = new ThreadGroup("Custom Thread Group");

        // 기본 스레드 그룹에 속한 스레드 생성
        Thread defaultGroupThread = new Thread(new GroupRunnable(), "DefaultGroupThread");

        // 메인 스레드 그룹에 속한 스레드 생성
        Thread mainGroupThread = new Thread(mainGroup, new GroupRunnable(), "MainGroupThread");

        // 직접 생성한 스레드 그룹에 속한 스레드 생성
        Thread customGroupThread = new Thread(customGroup, new GroupRunnable(), "CustomGroupThread");

        defaultGroupThread.start();
        mainGroupThread.start();
        customGroupThread.start();
    }

    static class GroupRunnable implements Runnable {
        @Override
        public void run() {
            Thread currentThread = Thread.currentThread();
            System.out.println(currentThread.getName() + " 는 " + currentThread.getThreadGroup().getName() + " 에 속한다");
        }
    }
}
