package io.concurrency.chapter04.exam04;

public class    NestedThreadGroupExample {
    public static void main(String[] args) throws InterruptedException {

        // 최상위 스레드 그룹 생성
        ThreadGroup topGroup = new ThreadGroup("최상위 스레드 그룹");

        // 최상위 스레드 그룹 내부에 하위 스레드 그룹 생성
        ThreadGroup subGroup = new ThreadGroup(topGroup, "하위 스레드 그룹");

        // 최상위 스레드 그룹에 속한 스레드 생성
        Thread topGroupThread = new Thread(topGroup, new MyRunnable(), "TopGroupThread");

        // 하위 스레드 그룹에 속한 스레드 생성
        Thread subGroupThread = new Thread(subGroup, new MyRunnable(), "SubGroupThread");

        // 그룹 정보 출력
        System.out.println(topGroupThread.getName() + " 는 " + topGroupThread.getThreadGroup().getName() + "에 속해 있습니다.");
        System.out.println(subGroupThread.getName() + " 는 " + subGroupThread.getThreadGroup().getName() + "에 속해 있습니다.");

        topGroupThread.start();
        subGroupThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("-----------------------------------------------------------");
        // 최상위 스레드 그룹의 정보 출력
        System.out.println("최상위 스레드 그룹의 정보:");
        topGroup.list();
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " : " + Thread.currentThread().getThreadGroup().getName());
        }
    }
}
