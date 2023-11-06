package io.concurrency.chapter05.exam04;

public class ThreadSafeLocalVariableExample {

//    int localSum = 0;
public void printNumbers(int plus) {
    // 지역 변수, 매개 변수로 정의된 변수. 각 스레드는 이 변수의 독립된 복사본을 가짐.
    int localSum = 0;

    for (int i = 1; i <= 5; i++) {
        localSum += i;
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    localSum += plus;
    System.out.println(Thread.currentThread().getName() + " - 현재 합계: " + localSum);
}

    public static void main(String[] args) {
        ThreadSafeLocalVariableExample example = new ThreadSafeLocalVariableExample();

        Thread thread1 = new Thread(() -> {
            example.printNumbers(50);
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            example.printNumbers(40);
        }, "Thread-2");

        thread1.start();
        thread2.start();
    }
}
