package io.concurrency.chapter07.exam04;

public class LivelockExample {

    static class Worker {
        private boolean isBusy;
        private String name;

        public Worker(String name) {
            this.name = name;
            this.isBusy = true;
        }

        public boolean isBusy() {
            return isBusy;
        }

        public void doWorkWith(Worker partner) {
            while (isBusy) {
                if (partner.isBusy()) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println(name + ": " + "다른 스레드에게 실행을 양보한다");
                } else {
                    System.out.println(name + ": " + "작업중..");
                    isBusy = false;
                }
            }
        }
    }

    public static void main(String[] args) {
        Worker worker1 = new Worker("Worker 1");
        Worker worker2 = new Worker("Worker 2");

        new Thread(() -> worker1.doWorkWith(worker2)).start();
        new Thread(() -> worker2.doWorkWith(worker1)).start();

    }
}
