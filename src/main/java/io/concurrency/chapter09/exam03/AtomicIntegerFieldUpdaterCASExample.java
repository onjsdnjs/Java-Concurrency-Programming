package io.concurrency.chapter09.exam03;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterCASExample {
    static AtomicIntegerFieldUpdater<MyClass> fieldUpdater;

    public static class MyClass {
        volatile int counter;

        public int getCounter() {
            return counter;
        }
    }

    public static void main(String[] args) {
        fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(MyClass.class, "counter");

        MyClass instance = new MyClass();
        int numThreads = 3;
        int numIterations = 10000;

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < numIterations; j++) {
                    int currentValue;
                    int newValue;
                    do {
                        currentValue = fieldUpdater.get(instance);
                        newValue = currentValue + 1;
                    } while (!fieldUpdater.compareAndSet(instance, currentValue, newValue));
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int finalValue = fieldUpdater.get(instance);
        System.out.println("Final Counter Value: " + finalValue);
    }
}
