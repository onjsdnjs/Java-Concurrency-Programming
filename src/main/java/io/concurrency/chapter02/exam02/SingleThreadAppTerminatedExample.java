package io.concurrency.chapter02.exam02;

public class SingleThreadAppTerminatedExample {

    public static void main(String[] args) {
        System.out.println("프로그램 시작");

        // 단순한 작업 실행
        for (int i = 0; i < 5; i++) {
            System.out.println("작업 진행: " + i);
        }

        System.out.println("프로그램 종료");
    }

}
