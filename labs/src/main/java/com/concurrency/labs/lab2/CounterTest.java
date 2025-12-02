package com.concurrency.labs.lab2;

public class CounterTest {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    counter.increment("thread 1");


                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    counter.increment("thread 2");


                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final value: " + counter.value());
    }
}
