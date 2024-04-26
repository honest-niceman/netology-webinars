package com.netology.collections;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//https://github.com/netology-code/jd-homeworks/blob/video/concurrent_collections/task1/README.md
public class Analyzer {
    public static BlockingQueue<String> queueA = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queueB = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queueC = new ArrayBlockingQueue<>(100);

    public static Thread textGenerator;

    public static void main(String[] args) throws InterruptedException {
        textGenerator = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                String text = generateText("abc", 100000);
                try {
                    queueA.put(text);
                    queueB.put(text);
                    queueC.put(text);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        textGenerator.start();

        Thread a = createNewThread(queueA, 'a');
        Thread b = createNewThread(queueA, 'b');
        Thread c = createNewThread(queueA, 'c');

        a.start();
        b.start();
        c.start();

        a.join();
        b.join();
        c.join();
    }

    public static Thread createNewThread(BlockingQueue<String> queue, char letter) {
        return new Thread(() -> {
            int max = findMaxCharCount(queue, letter);
            System.out.println("Max quantity of " + letter + " is " + max);
        });
    }

    public static int findMaxCharCount(BlockingQueue<String> queue, char letter) {
        int count = 0;
        int max = 0;
        String text;
        try {
            text = queue.take();
            for (char c : text.toCharArray()) {
                if (c == letter) {
                    count++;
                }
            }
            if (count > max) {
                max = count;
            }
         } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " interrupted");
            return -1;
        }
        return max;
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
