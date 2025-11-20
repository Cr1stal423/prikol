package com.concurrency.labs.lab1;

import static java.rmi.server.LogStream.log;

public class ChickenEggDebate {
    static class ChickenThread extends Thread {
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

    private static long randomMillis() {
        return  (long) (Math.random() * 1000);
    }
}
