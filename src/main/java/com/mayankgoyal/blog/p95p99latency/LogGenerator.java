package com.mayankgoyal.blog.p95p99latency;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class LogGenerator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java LogGenerator <number_of_lines>");
            System.exit(1);
        }

        int numberOfLines = Integer.parseInt(args[0]);

        try (PrintWriter writer = new PrintWriter(new FileWriter("log.txt"))) {
            for (int i = 0; i < numberOfLines; i++) {
                String logLine = generateLogLine();
                writer.println(logLine);
            }
            System.out.println("Log file generated successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    private static String generateLogLine() {
        Random random = new Random();
        String[] requestTypes = {"ServiceA.processRequestTypeOne()",
                "ServiceA.processRequestTypeTwo()",
                "ServiceA.processRequestTypeThree()",
                "ServiceA.processRequestTypeFour()"};

        Instant timestamp = Instant.now(); // Random timestamp within the past year
        String formattedTimestamp = FORMATTER.format(timestamp.atZone(ZoneId.systemDefault()));

        String traceId = "traceId=" + random.nextInt(1000000000);
        String threadId = "threadId=" + (random.nextInt(4) + 1); // Limit to 4 threads per request type
        String requestType = requestTypes[random.nextInt(4)];
        int executionTime = random.nextInt(100) + 1; // Random execution time between 1 and 100 ms

        return formattedTimestamp + " [" + traceId + "] [" + threadId + "] " + requestType + " executed in " + executionTime + " ms";
    }
}