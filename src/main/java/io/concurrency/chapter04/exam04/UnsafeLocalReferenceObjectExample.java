package io.concurrency.chapter04.exam04;

import java.util.Random;

public class UnsafeLocalReferenceObjectExample {
    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();

        UserProfile userProfile = new UserProfile();

        Address localAddress = new Address("서울 특별시"); // 지역 참조 변수

        // 스레드 1: UserProfile 에서 주소 변경
        Thread thread1 = new Thread(() -> {
            localAddress.setStreet("100 번가");
            userProfile.setAddress(localAddress);
            System.out.println("Thread-1: " + userProfile.getAddress().getStreet());
        });

        // 스레드 2: UserProfile 에서 주소 변경
        Thread thread2 = new Thread(() -> {
            localAddress.setStreet("200 번가");
            userProfile.setAddress(localAddress);
            System.out.println("Thread-2: " + userProfile.getAddress().getStreet());
        });

        if(random.nextInt(3) / 2 == 0 ) {
            thread1.start();
            thread2.start();
        }else{
            thread2.start();
            thread1.start();
        }

        thread1.join();
        thread2.join();

        System.out.println("userProfile: " + userProfile);
    }
}
class Address {
    private String street;
    public Address(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public synchronized void setStreet(String street) {
        try {
            Thread.sleep(500); // 일부러 지연을 주어 경쟁 조건을 증가 시킨다.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                '}';
    }
}

class UserProfile {
    private Address address;

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "address=" + address +
                '}';
    }
}