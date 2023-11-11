package io.concurrency.chapter11.exam01;

public class AsynchronousCall {

    public static void main(String[] args) {
        // 비동기 실행
        asyncCall1();
        System.out.println("메인 스레드 종료");
    }

    public static void asyncCall1() {

            new Thread(() -> {
                try {
                    //비동기 실행
                    asyncCall2();
                    System.out.println("기다리지 않고 계속 작업 진행합니다");
                    asyncCall3();
                    System.out.println("동기 작업을 기다립니다.");
                    Thread.sleep(1000); // 1초 대기
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();

    }

    private static void asyncCall2() {

        new Thread(() -> {
            try {
                System.out.println("비동기 작업 합니다");
                Thread.sleep(1000); // 1초 대기
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }).start();

    }

    private static void asyncCall3() {

    try {
        System.out.println("동기 작업 합니다");
        Thread.sleep(1000); // 1초 대기
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }

    }
}
