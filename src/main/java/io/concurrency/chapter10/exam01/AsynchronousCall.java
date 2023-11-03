package io.concurrency.chapter10.exam01;

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
                    System.out.println("asyncCall1 종료");
                    Thread.sleep(1000); // 1초 대기
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();

    }

    private static void asyncCall2() {

        new Thread(() -> {
            try {
                System.out.println("asyncCall2 종료");
                Thread.sleep(1000); // 1초 대기
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }).start();

    }
}
