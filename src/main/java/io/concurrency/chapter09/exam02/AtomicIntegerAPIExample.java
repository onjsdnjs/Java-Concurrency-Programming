package io.concurrency.chapter09.exam02;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class AtomicIntegerAPIExample {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInt = new AtomicInteger(10);
        int currentValue = atomicInt.get();
        System.out.println("Current value: " + currentValue); // 10

        atomicInt.set(20);
        System.out.println("New value: " + atomicInt.get()); // 20

        int previousValue = atomicInt.getAndSet(30);
        System.out.println("Previous value: " + previousValue); // 20 System.out.println("New value: " + atomicInt.get()); // 30

        int newValue = atomicInt.incrementAndGet();
        System.out.println("New value after increment: " + newValue); // 31

        boolean updated = atomicInt.compareAndSet(31, 40);
        System.out.println("Update successful? " + updated);  // true
        System.out.println("New value: " + atomicInt.get()); // 40

        IntUnaryOperator addFive = value -> value + 5;
        int previousValue2 = atomicInt.getAndUpdate(addFive);
        System.out.println("Previous value: " + previousValue2); // 40 System.out.println("Updated value: " + atomicInt.get());  // 45
        System.out.println("Next value: " + atomicInt.get()); // 40 System.out.println("Updated value: " + atomicInt.get());  // 45

    }
}
