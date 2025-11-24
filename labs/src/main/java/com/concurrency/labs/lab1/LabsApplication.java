package com.concurrency.labs.lab1;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.concurrency.labs.lab1.LogUtils.log;

@SpringBootApplication
public class LabsApplication {

    public static void main(String[] args) throws InterruptedException {

        log("\n---------- Start ----------");

        ChickenEggDebate.ChickenThread chickenThread =
                new ChickenEggDebate.ChickenThread();
        chickenThread.setName("Chicken Thread");

        Thread eggThread =
                new Thread(new ChickenEggDebate.EggThread(), "Egg Thread");

        Thread dogThread =
                new Thread(new ChickenEggDebate.DogThread(), "Dog Thread");


        log("Chicken Thread ID: " + chickenThread.getId());
        log("Chicken Thread name: " + chickenThread.getName());
        log("Egg Thread ID: " + eggThread.getId());
        log("Egg Thread name: " + eggThread.getName());
        log("Dog Thread ID: " + dogThread.getId());
        log("Dog Thread name: " + dogThread.getName());

        Thread.sleep(1000);

        log("\n---------- Start Threads ----------");

        chickenThread.start();
        eggThread.start();
        dogThread.start();

        chickenThread.join();
        eggThread.join();
        dogThread.join();

        String winner = LogUtils.getLastWriter();

        log("End: The winner is: " + winner);
    }
}
