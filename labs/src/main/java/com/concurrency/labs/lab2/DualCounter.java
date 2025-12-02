package com.concurrency.labs.lab2;

class DualCounter {
    private int counter1 = 0;
    private int counter2 = 0;
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void incrementCounter1(String threadName) throws InterruptedException {
        synchronized (lock1) {
            Thread.sleep(500);
            counter1++;
            System.out.println(threadName + " increment counter1: " + counter1);
        }
    }

    public void incrementCounter2(String threadName) throws InterruptedException {
        synchronized (lock2) {
            Thread.sleep(500);
            counter2++;
            System.out.println(threadName + " increment counter2: " + counter2);
        }
    }

    public int valueCounter1() {
        synchronized (lock1) {
            return counter1;
        }
    }

    public int valueCounter2() {
        synchronized (lock2) {
            return counter2;
        }
    }
}
