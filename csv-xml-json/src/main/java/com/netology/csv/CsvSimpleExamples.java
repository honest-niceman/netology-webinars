package com.netology.csv;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class CsvSimpleExamples {
    public static void main(String[] args) {
        System.out.println("=== write() ===");
        write();
        System.out.println("=== read() ===");
        read();
        System.out.println("=== readAll() ===");
        readAll();
        System.out.println("=== append() ===");
        append();
        System.out.println("=== readAll() ===");
        readAll();
        System.out.println("=== customSeparator() ===");
        customSeparator();
    }

    private static void customSeparator() {
        CSVParser csvParser = new CSVParserBuilder()
                .withSeparator('|')
                .build();

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader("csv-xml-json/src/main/resources/csv/all-staff-separated.csv"))
                .withCSVParser(csvParser)
                .build()
        ) {
            var strings = csvReader.readAll();
            strings.forEach(row -> System.out.println(Arrays.toString(row)));
        } catch (IOException | CsvException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void append() {
        var employee = "1,New,Employee,USA,1".split(",");
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter("csv-xml-json/src/main/resources/csv/all-staff.csv", true))) {
            csvWriter.writeNext(employee);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void readAll() {
        try (CSVReader csvReader = new CSVReader(new FileReader("csv-xml-json/src/main/resources/csv/all-staff.csv"))) {
            var strings = csvReader.readAll();
            strings.forEach(row -> System.out.println(Arrays.toString(row)));
        } catch (IOException | CsvException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void read() {
        try (CSVReader csvReader = new CSVReader(new FileReader("csv-xml-json/src/main/resources/csv/staff.csv"))) {
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                System.out.println(Arrays.toString(nextLine));
            }
        } catch (CsvValidationException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void write() {
        var employee = "1,David,Miller,Australia,30".split(",");
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter("csv-xml-json/src/main/resources/csv/staff.csv"))) {
            csvWriter.writeNext(employee);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
