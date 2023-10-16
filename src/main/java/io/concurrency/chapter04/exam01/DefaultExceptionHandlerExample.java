package io.concurrency.chapter04.exam01;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultExceptionHandlerExample {
    private static final Logger LOGGER = Logger.getLogger(DefaultExceptionHandlerExample.class.getName());

    public static void main(String[] args) {

        // 모든 스레드의 예외에 대한 기본 핸들러 설정
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            LOGGER.log(Level.SEVERE, t.getName() + "에서 예외 발생", e);

            // 오류 알림 보내기
            sendNotificationToAdmin(e);
        });

        // 예외를 발생시키는 여러 스레드
        Thread thread1 = new Thread(() -> {
            throw new RuntimeException("스레드 1 예외!");
        });

        Thread thread2 = new Thread(() -> {
            throw new RuntimeException("스레드 2 예외!");
        });

        thread1.start();
        thread2.start();
    }

    private static void sendNotificationToAdmin(Throwable e) {
        System.out.println("관리자에게 알림: " + e.getMessage());
    }
}
