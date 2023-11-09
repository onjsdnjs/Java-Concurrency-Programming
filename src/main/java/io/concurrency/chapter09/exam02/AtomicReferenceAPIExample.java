package io.concurrency.chapter09.exam02;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

public class AtomicReferenceAPIExample {

    public static void main(String[] args) throws InterruptedException {
        AtomicReference<String> reference = new AtomicReference<>("Initial Value");
        String currentValue = reference.get();
        System.out.println("Current value: " + currentValue); // Initial Value

        reference.set("New Value");
        System.out.println("New value: " + reference.get()); // New Value

        boolean success = reference.compareAndSet("New Value", "Updated Value");
        System.out.println("Update successful? " + success);  // true
        System.out.println("Current value: " + reference.get()); // Updated Value

        String oldValue = reference.getAndSet("Final Value");
        System.out.println("Old value: " + oldValue);  // Updated Value
        System.out.println("Current value: " + reference.get()); // Final Value

        UnaryOperator<String> operator = oldValue2 -> oldValue2 + " is correct";
        String newValue = reference.getAndUpdate(operator);
        System.out.println("New value: " + newValue);  // Final Value
        System.out.println("Current value: " + reference.get()); // Final Value is correnct
    }
}
