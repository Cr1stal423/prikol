package com.concurrency.labs.lab3;

public class DeadlockDemo {
    static class Conflict {
        private final String name;

        public Conflict(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public synchronized void bow(Conflict bower) {
            System.out.format("%s: %s пропускає мене!%n", this.name, bower.getName());
            bower.bowBack(this);
        }

        public synchronized void bowBack(Conflict bower) {
            System.out.format("%s: %s пропускає мене у відповідь!%n", this.name, bower.getName());
        }
    }

    static class ConflictGuarded {
        private final String name;
        private boolean isBusy = false;

        public ConflictGuarded(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public synchronized void bow(ConflictGuarded bower) {
            // Mark as busy
            isBusy = true;
            System.out.format("%s: %s пропускає мене!%n", this.name, bower.getName());

            // Check if the other is busy
            if (bower.isBusy) {
                System.out.println("Deadlock detected! " + this.name + " is releasing the lock...");
                // Release the lock and wait
                try {
                    wait(100); // Wait for 100ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            bower.bowBack(this);
            isBusy = false; // Mark as not busy
            notifyAll(); // Notify waiting threads
        }

        public synchronized void bowBack(ConflictGuarded bower) {
            System.out.format("%s: %s пропускає мене у відповідь!%n", this.name, bower.getName());
        }
    }

    public static void main(String[] args) {
        // 1. Demonstrate deadlock
        System.out.println("=== Deadlock Demonstration ===");
        final Conflict alphonse = new Conflict("Alphonse");
        final Conflict gaston = new Conflict("Gaston");

        new Thread(() -> alphonse.bow(gaston)).start();
        new Thread(() -> gaston.bow(alphonse)).start();

        // Wait for deadlock to occur and then interrupt
        try {
            Thread.sleep(2000);
            System.out.println("\n=== Resolving Deadlock with Guarded Suspension ===");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 2. Demonstrate guarded suspension solution
        final ConflictGuarded alphonseGuarded = new ConflictGuarded("Alphonse (Guarded)");
        final ConflictGuarded gastonGuarded = new ConflictGuarded("Gaston (Guarded)");

        new Thread(() -> alphonseGuarded.bow(gastonGuarded)).start();
        new Thread(() -> gastonGuarded.bow(alphonseGuarded)).start();
    }
}
