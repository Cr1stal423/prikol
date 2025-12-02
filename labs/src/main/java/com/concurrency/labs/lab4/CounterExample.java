package com.concurrency.labs.lab4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterExample {

    static class Counter {
        private int c = 0;

        public void increment() throws InterruptedException {
            int a;
            Thread.sleep((int)(Math.random() * 10));
            Thread.sleep(150);
            a = c;
            Thread.sleep((int)(Math.random() * 10));
            a++;
            c = a;
        }

        public int value() {
            return c;
        }
    }

    public static void main(String[] args) {
        int numThreads = 5; // ← тут задаємо кількість потоків вручну

        Counter counter = new Counter();
        Lock lock = new ReentrantLock();

        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                try {
                    if (lock.tryLock(10, TimeUnit.SECONDS)) {
                        try {
                            counter.increment();
                            System.out.println(Thread.currentThread().getName() +
                                    " виконав increment #" + (i + 1) +
                                    " → значення: " + counter.value());
                        } finally {
                            lock.unlock();
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() +
                                " не зміг отримати блокування");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Створюємо ThreadPool
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Відправляємо задачі у пул
        for (int i = 0; i < numThreads; i++) {
            executor.submit(task);
        }

        // Закриваємо пул
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Фінальне значення лічильника: " + counter.value());
    }
}
