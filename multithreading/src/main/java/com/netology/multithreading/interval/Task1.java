package com.netology.multithreading.interval;

import java.util.Random;
import java.util.*;

//https://github.com/netology-code/jd-homeworks/blob/video/multithreading/task1/README.md

public class Task1 {
    public static void main(String[] args) throws InterruptedException {
        SingleThread.main(); //Time: 42776ms
        MultipleThreads.main(); //Time: 3334ms
    }
}

//Дано
class SingleThread {

    public static void main() throws InterruptedException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        long startTs = System.currentTimeMillis(); // start time
        for (String text : texts) {
            int maxSize = 0;
            for (int i = 0; i < text.length(); i++) {
                for (int j = 0; j < text.length(); j++) {
                    if (i >= j) {
                        continue;
                    }
                    boolean bFound = false;
                    for (int k = i; k < j; k++) {
                        if (text.charAt(k) == 'b') {
                            bFound = true;
                            break;
                        }
                    }
                    if (!bFound && maxSize < j - i) {
                        maxSize = j - i;
                    }
                }
            }
            System.out.println(text.substring(0, 100) + " -> " + maxSize);
        }
        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Time: " + (endTs - startTs) + "ms");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}

//Решение
class MultipleThreads {
    public static void main() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        long startTs = System.currentTimeMillis();
        for (String text : texts) {
            threads.add(createNewThread(text));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long endTs = System.currentTimeMillis();

        System.out.println("Time: " + (endTs - startTs) + "ms");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static Thread createNewThread(String text) {
        return new Thread(() -> {
           int maxSize = 0;
           for (int i = 0; i < text.length(); i++) {
               for (int j = 0; j < text.length(); j++) {
                   if (i >= j) {
                       continue;
                   }
                   boolean bFound = false;
                   for (int k = i; k < j; k++) {
                       if (text.charAt(k) == 'b') {
                           bFound = true;
                           break;
                       }
                   }
                   if (!bFound && maxSize < j - i) {
                       maxSize = j - i;
                   }
               }
           }
           System.out.println(text.substring(0, 100) + " -> " + maxSize);
        });
    }
}
