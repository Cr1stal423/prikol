package com.concurrency.labs.lab3;

public class DeadlockResolver {
    static class Conflict {
        private final String name;
        private static boolean busy = false;
        private static int stage = 0; // 0 = A починає, 1 = B відповідає, 2 = B починає, 3 = A відповідає

        public Conflict(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public synchronized void bow(Conflict bower) {
            try {
                while (!isAllowedStage(stage, name)) {
                    wait();
                }

                busy = true;
                System.out.format("%s: намагаюсь пропустити %s!%n", this.name, bower.getName());
                Thread.sleep(150);

                bower.bowBack(this);

                busy = false;
                stage++;
                notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        public synchronized void bowBack(Conflict bower) throws InterruptedException {
            System.out.format("%s: %s пропускає мене у відповідь!%n", this.name, bower.getName());
            Thread.sleep(150);
            stage++;
            notifyAll();
        }

        private boolean isAllowedStage(int stage, String name) {
            return (stage == 0 && name.equals("A")) ||
                    (stage == 2 && name.equals("B"));
        }

    }

    public static void main(String[] args) {
        Conflict conflict1 = new Conflict("A");
        Conflict conflict2 = new Conflict("B");

        Thread t1 = new Thread(() -> conflict1.bow(conflict2));
        Thread t2 = new Thread(() -> {
            try {
                conflict2.bow(conflict1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }
}
