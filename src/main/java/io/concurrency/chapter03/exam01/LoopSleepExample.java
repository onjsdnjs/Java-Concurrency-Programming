package io.concurrency.chapter03.exam01;

public class LoopSleepExample {
    public static void main(String[] args) {

        for(int i =0; i<7; i++) {
            try {
                System.out.println("반복: " + (i + 1) );
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
