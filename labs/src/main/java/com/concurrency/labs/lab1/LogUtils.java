package com.concurrency.labs.lab1;

public class LogUtils {

    private static String lastWriter = null;

    public static synchronized void log(String message) {
        lastWriter = Thread.currentThread().getName();
        System.out.println(message);
    }

    public static synchronized String getLastWriter() {
        return lastWriter;
    }
}
