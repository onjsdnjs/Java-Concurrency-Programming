package io.concurrency.chapter07.exam04;

public class NonDeadlockObjectsExample {
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

        public void methodA(ResourceB resourceB) {
            synchronized (ResourceA.class) { // 첫 번째로 ResourceA 락 획득
                System.out.println(Thread.currentThread().getName() + ": methodA의 ResourceA 부분 실행");

                synchronized (ResourceB.class) { // 두 번째로 ResourceB 락 획득
                    System.out.println(Thread.currentThread().getName() + ": methodA의 ResourceB 부분 실행");
                    resourceB.methodB2();
                }
            }
        }

        public void methodA2() {
            synchronized (ResourceA.class) {
                System.out.println(Thread.currentThread().getName() + ": methodA2 실행");
            }
        }
    }

    static class ResourceB {

        public void methodB(ResourceA resourceA) {
            synchronized (ResourceA.class) { // 첫 번째로 ResourceA 락 획득
                System.out.println(Thread.currentThread().getName() + ": methodB의 ResourceA 부분 실행");

                synchronized (ResourceB.class) { // 두 번째로 ResourceB 락 획득
                    System.out.println(Thread.currentThread().getName() + ": methodB의 ResourceB 부분 실행");
                    resourceA.methodA2();
                }
            }
        }

        public void methodB2() {
            synchronized (ResourceB.class) {
                System.out.println(Thread.currentThread().getName() + ": methodB2 실행");
            }
        }
    }
}




