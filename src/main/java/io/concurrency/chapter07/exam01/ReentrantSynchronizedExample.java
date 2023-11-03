package io.concurrency.chapter07.exam01;

class Parent {
    public synchronized void parentMethod() {
        System.out.println("부모 메서드를 호출함");
    }
}

class Child extends Parent {

    public synchronized void childMethod() {
        System.out.println("자식 메서드를 호출함");
        parentMethod();  // 부모 메서드를 호출
    }
}

public class ReentrantSynchronizedExample {

    public static void main(String[] args) {
        Child child = new Child();

        Thread t1 = new Thread(() -> {
            child.childMethod();
        });

        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
