package io.concurrency.chapter04.exam03;

public class UserAndDaemonInheritanceExample {
    public static void main(String[] args) {

        // 사용자 스레드 생성
        Thread userThread = new Thread(() -> {
            Thread childOfUserThread = new Thread(() -> {
                System.out.println("사용자 스레드의 자식 스레드의 데몬 상태: " + Thread.currentThread().isDaemon());
            });
            childOfUserThread.start();
            System.out.println("사용자 스레드의 데몬 상태: " + Thread.currentThread().isDaemon());
        });

        // 데몬 스레드 생성
        Thread daemonThread = new Thread(() -> {
            Thread childOfDaemonThread = new Thread(() -> {
                System.out.println("데몬 스레드의 자식 스레드의 데몬 상태: " + Thread.currentThread().isDaemon());
            });
            childOfDaemonThread.start();
            System.out.println("데몬 스레드의 데몬 상태: " + Thread.currentThread().isDaemon());
        });
        daemonThread.setDaemon(true);

        userThread.start();
        daemonThread.start();
    }
}
