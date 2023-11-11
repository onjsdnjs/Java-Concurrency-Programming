package io.concurrency.chapter11.exam01;

public class SynchronousCall {

    public static void main(String[] args) {
        // 동기 실행
        syncCall1();
        System.out.println("메인 스레드 종료");
    }

    public static void syncCall1() {

        try {
            // 동기 실행
            syncCall2();
            Thread.sleep(1000); // 1초 대기
            System.out.println("동기 실행 완료-1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void syncCall2() {

        try {
            Thread.sleep(1000); // 1초 대기
            System.out.println("동기 실행 완료-2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
