package com.netology.synchronization.frequency;

import java.util.*;

//https://github.com/netology-code/jd-homeworks/blob/video/synchronization/task2/README.md
public class Task2 {
    public static final String LETTERS = "RLRFR";
    public static final int ROUTE_LENGTH = 100;
    public static final int AMOUNT_OF_THREADS = 100;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        Thread printer = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (sizeToFreq) {
                    try {
                        sizeToFreq.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                    printLeader();
                }
            }
        });
        printer.start();

        for (int i = 0; i < AMOUNT_OF_THREADS; i++) {
            threads.add(createNewThread());
        }

        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }

        printer.interrupt();
    }

    public static Thread createNewThread() {
        return new Thread(() -> {
            String route = generateRoute(LETTERS, ROUTE_LENGTH);
            int frequency = (int) route.chars().filter(ch -> ch == 'R').count();
            synchronized (sizeToFreq) {
                sizeToFreq.put(frequency, sizeToFreq.getOrDefault(frequency, 0) + 1);
                sizeToFreq.notify();
            }
        });
    }

    public static void printLeader() {
        Map.Entry<Integer, Integer> max = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.printf("Текущий лидер%d (встретилось %d раз)%n", max.getKey(), max.getValue());
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
