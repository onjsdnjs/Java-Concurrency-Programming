package io.concurrency.chapter09.exam03;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicIntegerCompareExample {
    public static class MyClass {
        private AtomicInteger field1 = new AtomicInteger();
        private AtomicReference<String> field2 = new AtomicReference<>();

        public int getField1() {
            return field1.addAndGet(42);
        }

        public String getField2() {

            String currentValue;

            if (field2.compareAndSet(null, "myField")) {
                return "myField";
            } else {
                currentValue = field2.get();
            }

            return currentValue;
        }
    }

    public static void main(String[] args) {
        MyClass instance = new MyClass();
        System.out.println("Updated value: " + instance.getField1());
        System.out.println("Updated value: " + instance.getField2());
    }
}
