package com.concurrency.labs.lab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.concurrency.labs.lab1.LogUtils.log;

@SpringBootApplication
public class LabsApplication {

    public static void main(String[] args) throws InterruptedException {
        Thread eggThread;
        ChickenEggDebate.ChickenThread chickenThread;
        for (int i = 0; i < 10; i++) {
            log("\n---------- Start ----------");
            // Thread
            chickenThread = new ChickenEggDebate.ChickenThread();
            chickenThread.setName("Chicken Thread");

            // Runnable
            eggThread = new Thread(new ChickenEggDebate.EggThread(), "Egg Thread");

            log("Chicken Thread ID: " + chickenThread.getId());
            log("Chicken Thread name: " + chickenThread.getName());
            log("Egg Thread ID: " + eggThread.getId());
            log("Egg Thread name: " + eggThread.getName());

            Thread.sleep(1000);

            log("\n---------- Start ----------");
            chickenThread.start();
            eggThread.start();

            try {
                chickenThread.join();
                if (eggThread.isAlive()) {//this is a tiny gap when eggThread isAlive but after this line it finishes
                    eggThread.join();
                    log("End: The winner is: " + eggThread.getName());
                } else {
                    log("End: The winner is: " + chickenThread.getName());
                }
            } catch (InterruptedException e) {
                log("Main Thread has been interrupted");
            }
        }
    }
}