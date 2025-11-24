package com.concurrency.labs.lab1;

import static com.concurrency.labs.lab1.LogUtils.log;

public class ChickenEggDebate {

    static class ChickenThread extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    log("Chicken is first");
                    Thread.sleep(randomMillis());
                }
            } catch (InterruptedException e) {
                log("Chicken thread has been interrupted");
            }
        }
    }

    static class EggThread implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    log("Egg is first");
                    Thread.sleep(randomMillis());
                }
            } catch (InterruptedException e) {
                log("Egg thread has been interrupted");
            }
        }
    }

    static class DogThread implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    log("Dog is first");
                    Thread.sleep(randomMillis());
                }
            } catch (InterruptedException e) {
                log("Dog thread has been interrupted");
            }
        }
    }

    protected static long randomMillis() {
        return (long) (Math.random() * 1000);
    }
}
