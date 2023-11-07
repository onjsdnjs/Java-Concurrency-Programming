package io.concurrency.chapter07.exam01.block;

class MethodBlock{}
public class StaticBlockSynchronizedExamples {

    private static int count = 0;

    public static void incrementBlockClass(){
        synchronized (StaticBlockSynchronizedExamples.class){
            count++;
            System.out.println(Thread.currentThread().getName() + " 가 StaticBlockSynchronizedExamples 에 의해 블록 동기화 함 : " + count);
        }
    }
    public static void incrementBlockOtherClass() {
        synchronized (MethodBlock.class){
            count++;
            System.out.println(Thread.currentThread().getName() + " 가 MethodBlock 에 의해 블록 동기화 함 : " + count);
        }
    }

    public static void main(String[] args) {
        StaticBlockSynchronizedExamples example = new StaticBlockSynchronizedExamples();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                StaticBlockSynchronizedExamples.incrementBlockClass();
            }
        },"스레드 1");

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                StaticBlockSynchronizedExamples.incrementBlockOtherClass();
            }
        },"스레드 2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("최종값:"  + count);
    }
}
