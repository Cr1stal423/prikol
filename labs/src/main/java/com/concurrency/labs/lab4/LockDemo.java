package com.concurrency.labs.lab4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    static class Counter {
        private int c = 0;
        private final Lock lock = new ReentrantLock();

        public void increment() throws InterruptedException {
            boolean locked = false;
            try {
                // Try to acquire the lock with a timeout of 10 seconds
                locked = lock.tryLock(10, TimeUnit.SECONDS);
                if (locked) {
                    int a;
                    Thread.sleep(150);
                    a = c;
                    a++;
                    c = a;
                } else {
                    System.out.println(Thread.currentThread().getName() + " could not acquire lock, operation timed out");
                }
            } finally {
                // Only unlock if we successfully acquired the lock
                if (locked) {
                    lock.unlock();
                }
            }
        }

        public int value() {
            return c;
        }
    }

    public static void main(String[] args) {
        final Counter counter = new Counter();

        // Create and start first thread
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    counter.increment();
                    System.out.println(Thread.currentThread().getName() + " - Counter value: " + counter.value());
                    // Small delay to allow the other thread to get the lock
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread 1 was interrupted");
                    break;
                }
            }
        }, "Thread-1");

        // Create and start second thread
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    counter.increment();
                    System.out.println(Thread.currentThread().getName() + " - Counter value: " + counter.value());
                    // Small delay to allow the other thread to get the lock
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread 2 was interrupted");
                    break;
                }
            }
        }, "Thread-2");

        System.out.println("Starting threads...");
        thread1.start();
        thread2.start();

        // Wait for both threads to complete
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread was interrupted while waiting for threads to complete");
        }

        System.out.println("Final counter value: " + counter.value());
    }
}
