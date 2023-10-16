package io.concurrency.chapter03.exam01;

public class LoopSleepExample {
    public static void main(String[] args) {
        for (int i = 0; i < 7; i++) {
            System.out.println("반복: " + (i + 1));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
