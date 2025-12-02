package com.concurrency.labs.lab2;

class Counter {
    private int c = 0;

    public synchronized void increment(String threadName) throws InterruptedException {
        Thread.sleep(150);
        c ++;
        System.out.println("Thread 1 increment: " + c);
    }

    public synchronized void decrement() throws InterruptedException {
        int a;
        Thread.sleep(100);
        a = c;
        a--;
        c = a;
    }

    public synchronized int value() {
        return c;
    }
}
