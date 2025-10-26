package com.concurrency.labs.lab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LabsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabsApplication.class, args);

        System.out.println("Починаємо дебати!");

        // Thread
        ChickenEggDebate.ChickenThread chickenThread = new ChickenEggDebate.ChickenThread();
        chickenThread.setName("Курячий потік");

        // Runnable
        Thread eggThread = new Thread(new ChickenEggDebate.EggThread(), "Яєчний потік");

        System.out.println("ID потоку курки: " + chickenThread.getId());
        System.out.println("Ім'я потоку курки: " + chickenThread.getName());
        System.out.println("ID потоку яйця: " + eggThread.getId());
        System.out.println("Ім'я потоку яйця: " + eggThread.getName());

        chickenThread.start();
        eggThread.start();

        try {
            chickenThread.join();
            eggThread.join();

            System.out.println("Дебати завершено!");
        } catch (InterruptedException e) {
            System.out.println("Головний потік перервано!");
        }
    }
}
