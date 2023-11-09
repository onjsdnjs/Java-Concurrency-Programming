package io.concurrency.chapter09.exam02;

public class NonAtomicIntegerGetAndUpdateExample {
    private static int accountBalance = 1000; // 초기 계좌 잔고
    public static void main(String[] args) {
        // 여러 스레드에서 계좌 업데이트 작업 시뮬레이션
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                int withdrawalAmount = 100; // 출금액
                int updatedBalance = 0;

                synchronized (NonAtomicIntegerGetAndUpdateExample.class) {
                    if (accountBalance >= withdrawalAmount) {
                        updatedBalance = accountBalance - withdrawalAmount; // 출금 성공
                        accountBalance = updatedBalance;
                    }
                }

                if (updatedBalance < 0) {
                    System.out.println("잔고 부족으로 출금 실패");
                } else {
                    System.out.println("출금 후 잔고: " + updatedBalance);
                }
            }).start();
        }
    }
}
