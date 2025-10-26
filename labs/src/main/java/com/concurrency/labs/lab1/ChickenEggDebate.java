package com.concurrency.labs.lab1;

public class ChickenEggDebate {
    static class ChickenThread extends Thread {
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Курка була першою!");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Курячий потік перервано!");
            }
        }
    }

    static class EggThread implements Runnable {
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Ні, яйце було першим!");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("Яєчний потік перервано!");
            }
        }
    }
}
