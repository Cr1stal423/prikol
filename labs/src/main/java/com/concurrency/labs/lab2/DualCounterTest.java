package com.concurrency.labs.lab2;

public class DualCounterTest {
    public static void main(String[] args) throws InterruptedException {
        DualCounter dualCounter = new DualCounter();

        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    dualCounter.incrementCounter1("Thread 1");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    dualCounter.incrementCounter2("Thread 2");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    dualCounter.incrementCounter1("Thread 3");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t4 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    dualCounter.incrementCounter2("Thread 4");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("\n===== Final Results =====");
        System.out.println("Final counter1: " + dualCounter.valueCounter1());
        System.out.println("Final counter2: " + dualCounter.valueCounter2());


    }
}
