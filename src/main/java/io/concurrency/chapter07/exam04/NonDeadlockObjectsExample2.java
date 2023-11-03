package io.concurrency.chapter07.exam04;

public class NonDeadlockObjectsExample2 {
    public static void main(String[] args) {

        ResourceA resourceA = new ResourceA();
        ResourceB resourceB = new ResourceB();

        Thread thread1 = new Thread(() -> {
            resourceA.methodA(resourceB);
        });

        Thread thread2 = new Thread(() -> {
            resourceB.methodB(resourceA);
        });

        thread1.start();
        thread2.start();
    }

    static class ResourceA {

        public void methodA(ResourceB resourceB) { // 메서드 오픈
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + ": methodA 실행");
            }
            resourceB.methodB2();
        }

        public synchronized void methodA2(ResourceB resourceB) {
            System.out.println(Thread.currentThread().getName() + ": methodA2 실행");
        }
    }

    static class ResourceB {

        public void methodB(ResourceA resourceA) { // 메서드 오픈
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + ": methodB 실행");
            }
            resourceA.methodA2(this);
        }

        public synchronized void methodB2() {
            System.out.println(Thread.currentThread().getName() + ": methodB2 실행");
        }
    }
}




