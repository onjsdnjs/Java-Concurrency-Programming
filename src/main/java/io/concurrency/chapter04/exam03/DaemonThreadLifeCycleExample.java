package io.concurrency.chapter04.exam03;

public class DaemonThreadLifeCycleExample {
    public static void main(String[] args) {

        // 사용자 스레드
        Thread userThread = new Thread(() -> {
            try {
                Thread.sleep(3000); // 3초 동안 실행
                System.out.println("사용자 스레드 종료");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 데몬 스레드
        Thread daemonThread = new Thread(() -> {
            while (true) { // 무한루프
                try {
                    Thread.sleep(500); // 0.5초 마다 반복
                    System.out.println("데몬 스레드 실행 중");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        daemonThread.setDaemon(true); // 스레드를 데몬 스레드로 설정

        userThread.start();
        daemonThread.start();

//        daemonThread.setDaemon(true); // 스레드를 데몬 스레드로 설정

        try {
            userThread.join(); // 사용자 스레드가 종료될 때까지 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("메인 스레드 종료");
    }
}
