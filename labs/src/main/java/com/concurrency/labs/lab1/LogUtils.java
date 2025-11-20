package com.concurrency.labs.lab1;

public class LogUtils {
    public static synchronized void log(String message) {
        System.out.println(message);
    }
}
