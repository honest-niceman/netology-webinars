package com.netology.variables;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.netology.variables.Utils.*;

//https://github.com/netology-code/jd-homeworks/blob/video/volatile/README.md
public class Nicknames {
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindrome = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text) && !isSameChar(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        palindrome.start();

        Thread sameChar = new Thread(() -> {
            for (String text : texts) {
                if (isSameChar(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        sameChar.start();

        Thread ascendingOrder = new Thread(() -> {
            for (String text : texts) {
                if (isSameChar(text) && isAscendingOrder(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        ascendingOrder.start();

        sameChar.join();
        ascendingOrder.join();
        palindrome.join();

        System.out.println("Красивых слов длиной 3:" + counter3);
        System.out.println("Красивых слов длиной 4:" + counter4);
        System.out.println("Красивых слов длиной 5:" + counter5);
    }
}

class Utils {
    public static AtomicInteger counter3 = new AtomicInteger(0);
    public static AtomicInteger counter4 = new AtomicInteger(0);
    public static AtomicInteger counter5 = new AtomicInteger(0);

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        return text.contentEquals(new StringBuilder(text).reverse());
    }

    public static void incrementCounter(int textLength) {
        if (textLength == 3) {
            counter3.incrementAndGet();
        } else if (textLength == 4) {
            counter4.incrementAndGet();
        } else if (textLength == 5) {
            counter5.incrementAndGet();
        }
    }

    public static boolean isAscendingOrder(String text) {
        return isSameChar(text);
    }

    public static boolean isSameChar(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
}
