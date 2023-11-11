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
                    System.out.println("비동기 실행-2 를 기다리지 않습니다.");
                    System.out.println("비동기 실행-1 작업을 계속 수행합니다.");
                    Thread.sleep(1000); // 1초 대기
                    System.out.println("비동기 실행 완료-1");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();

    }

    private static void asyncCall2() {

        new Thread(() -> {
            try {
                Thread.sleep(1000); // 1초 대기
                System.out.println("비동기 실행 완료-2");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }).start();

    }

}
