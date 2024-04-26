package com.netology.multithreading.maxinterval;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

//https://github.com/netology-code/jd-homeworks/blob/video/multithreading/task2/README.md

public class Task2 {
    public static void main() throws ExecutionException, InterruptedException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }
        List<Future<Integer>> futureList = new ArrayList<>();
        int maxValue = 0;
        ExecutorService threadPool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        long startTs = System.currentTimeMillis();

        for (String text : texts) {
            futureList.add(threadPool.submit(new CallableTask(text)));
        }

        for (Future<Integer> future : futureList) {
            maxValue = Math.max(future.get(), maxValue);
        }

        System.out.println(maxValue);

        threadPool.shutdown();
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
}

class CallableTask implements Callable<Integer> {
    private final String text;

    public CallableTask(String text) {
        this.text = text;
    }

    @Override
    public Integer call() throws Exception {
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
        return maxSize;
    }
}