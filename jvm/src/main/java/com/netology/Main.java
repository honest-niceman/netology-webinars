package com.netology;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        simpleGarbageCollection();
        outOfMemoryExample();
    }

    private static void simpleGarbageCollection() {
        List<String> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 1_000_000_000; i++) {
            String s = " " + random.nextInt();
            list.add(s);
            if (i % 10 == 0) {
                list.remove(0);
            }
        }
    }

    private static void outOfMemoryExample() {
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        int[][] matrix = new int[Integer.MAX_VALUE / 100][];
        for (int i = 0; i < matrix.length; i++) {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            matrix[i] = new int[Integer.MAX_VALUE / 10];
        }
    }
}