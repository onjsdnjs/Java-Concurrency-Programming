package io.concurrency.chapter08.exam02;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExample {
    public static void main(String[] args) {

        User user1 = new User("Alice", 25);
        User user2 = new User("Bob", 30);

        AtomicReference<User> atomicReference = new AtomicReference<>(user1);

        Thread thread1 = new Thread(() -> {
            User updatedUser = new User("Carol", 28);
            boolean success = atomicReference.compareAndSet(user1, updatedUser);
            if (success) {
                System.out.println("Thread 1 updated user: " + updatedUser);
            } else {
                System.out.println("Thread 1 failed to update user.");
            }
        });

        Thread thread2 = new Thread(() -> {
            User updatedUser = new User("David", 35);
            boolean success = atomicReference.compareAndSet(user2, updatedUser);
            if (success) {
                System.out.println("Thread 2 updated user: " + updatedUser);
            } else {
                System.out.println("Thread 2 failed to update user.");
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final user: " + atomicReference.get());
    }
}

class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}