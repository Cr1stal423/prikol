package com.concurrency.labs.lab2;

import java.util.Scanner;

class Counter {
    private int c = 0;

    public void increment() throws InterruptedException {
        int a;
        Thread.sleep(150);
        a = c;
        a++;
        c = a;
    }

    public void decrement() throws InterruptedException {
        int a;
        Thread.sleep(100);
        a = c;
        a--;
        c = a;
    }

    public int value() {
        return c;
    }
}

class SynchronizedCounter {
    private int c = 0;

    public synchronized void increment() throws InterruptedException {
        int a;
        Thread.sleep(150);
        a = c;
        a++;
        c = a;
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

class DualSynchronizedCounter {
    private Integer counter1 = 0;
    private Integer counter2 = 0;
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void increment1() throws InterruptedException {
        Thread.sleep(150);
        synchronized (lock1) {
            int a;
            a = counter1;
            a++;
            counter1 = a;
        }
    }

    public void decrement1() throws InterruptedException {
        Thread.sleep(150);
        synchronized (lock1) {
            int a;
            a = counter1;
            a--;
            counter1 = a;
        }
    }

    public void increment2() throws InterruptedException {
        Thread.sleep(133);
        synchronized (lock2) {
            int a;
            a = counter2;
            a++;
            counter2 = a;
        }
    }

    public void decrement2() throws InterruptedException {
        Thread.sleep(100);
        synchronized (lock2) {
            int a;
            a = counter2;
            a--;
            counter2 = a;
        }
    }

    public int value1() {
        synchronized (lock1) {
            return counter1;
        }
    }

    public int value2() {
        synchronized (lock2) {
            return counter2;
        }
    }
}

public class SynchronizationDemo {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        var i = 1;
        while (i != 0) {
            System.out.print("Select test to run (1-4, 0-end): ");
            i = sc.nextInt();

            switch (i) {
                case 1 -> test1();
                case 2 -> test2();
                case 3 -> test3();
                case 4 -> test4();
            }
        }
    }

    private static void test1() {
        // Test 1: Unsynchronized Counter
        System.out.println("Test 1: Unsynchronized Counter");
        Counter counter = new Counter();

        Thread thread1 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    counter.increment();
                    System.out.println("Thread 1 - Counter value: " + counter.value());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    counter.increment();
                    System.out.println("Thread 2 - Counter value: " + counter.value());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
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
    }

    private static void test2() {
        // Test 1: Unsynchronized Counter with Decrement
        System.out.println("\nTest 2: Unsynchronized Counter with Decrement");
        Counter counter2 = new Counter();

        Thread thread3 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    counter2.decrement();
                    System.out.println("Thread 3 - Counter value: " + counter2.value());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread4 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    counter2.decrement();
                    System.out.println("Thread 4 - Counter value: " + counter2.value());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread3.start();
        thread4.start();

        try {
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void test3() {
        // Test 3: Synchronized Counter
        System.out.println("\nTest 3: Synchronized Counter");
        SynchronizedCounter syncCounter = new SynchronizedCounter();

        Thread thread5 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    syncCounter.increment();
                    System.out.println("Thread 5 - Synchronized Counter value: " + syncCounter.value());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread6 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    syncCounter.increment();
                    System.out.println("Thread 6 - Synchronized Counter value: " + syncCounter.value());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread5.start();
        thread6.start();

        try {
            thread5.join();
            thread6.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void test4() throws InterruptedException {
        // Test 4: Dual Counter with synchronized blocks
        System.out.println("\nTest 4: Dual Counter with synchronized blocks");
        DualSynchronizedCounter dualSynchronizedCounter = new DualSynchronizedCounter();

        Thread thread7 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    dualSynchronizedCounter.increment1();
                    dualSynchronizedCounter.increment2();
                    System.out.println("Thread 7 - Counter1: " + dualSynchronizedCounter.value1());
                    System.out.println("Thread 7 - Counter2: " + dualSynchronizedCounter.value2());
                    Thread.sleep(310);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread8 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(310);
                    dualSynchronizedCounter.increment1();
                    dualSynchronizedCounter.increment2();
                    System.out.println("Thread 8 - Counter1: " + dualSynchronizedCounter.value1());
                    System.out.println("Thread 8 - Counter2: " + dualSynchronizedCounter.value2());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread7.start();
        thread8.start();

        try {
            thread7.join();
            thread8.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}