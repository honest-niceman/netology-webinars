package com.netology;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@FunctionalInterface
public interface TimeExecution {
    void execute() throws IOException;

    static void measureExecutionTime(TimeExecution execution) {
        Instant start = Instant.now();

        try {
            execution.execute();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        double l = (double) duration.toMillis() / 1_000;
        System.out.println("Execution time = " + l + " seconds.");
    }
}
