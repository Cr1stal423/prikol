package com.concurrency.labs.lab3;

public class Deadlock {
    static class Conflict {
        private final String name;
        private static boolean busy = false;

        public Conflict(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        // synchronized залишився, але тепер синхронізуємося на класі Conflict
        public synchronized void bow(Conflict bower) {
            synchronized (Conflict.class) {
                while (busy) {
                    try {
                        Conflict.class.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                busy = true;
            }

            System.out.format("%s: намагаюсь пропустити %s!%n", this.name, bower.getName());
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            bower.bowBack(this);

            synchronized (Conflict.class) {
                busy = false;
                Conflict.class.notifyAll();
            }
        }

        public synchronized void bowBack(Conflict bower) {
            System.out.format("%s: %s пропускає мене у відповідь!%n", this.name, bower.getName());
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Conflict conflict1 = new Conflict("A");
        Conflict conflict2 = new Conflict("B");

        Thread t1 = new Thread(() -> conflict1.bow(conflict2));
        Thread t2 = new Thread(() -> conflict2.bow(conflict1));

        t1.start();
        t2.start();
    }
}
