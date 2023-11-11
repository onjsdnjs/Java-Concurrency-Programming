package io.concurrency.chapter11.exam01;

public class SynchronousCall {

    public static void main(String[] args) {

        // 동기 실행
        int result = syncCall();
        System.out.println("result = " + result);
        System.out.println("메인 스레드 종료");
    }

    public static int syncCall() {

        try {
            // 동기 실행
            Thread.sleep(1000); // 1초 대기
            System.out.println("동기 실행 완료-1");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 10;
    }
}
