package com.netology.csv;

import com.netology.entity.Employee;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CsvComplexExamples {


    public static void main(String[] args) {
        System.out.println("=== readToObjects() ===");
        List<Employee> employees = readToObjects();
        System.out.println("=== writeObjects() ===");
        writeObjects(employees);
        System.out.println("=== writerWithStrategy() ===");
        writerWithStrategy(employees);
    }

    private static void writerWithStrategy(List<Employee> employees) {
        var strategy = new ColumnPositionMappingStrategy<Employee>();
        strategy.setType(Employee.class);
        strategy.setColumnMapping("id", "lastName", "firstName", "country", "age");

        try (Writer writer = new FileWriter("csv-xml-json/src/main/resources/csv/all-staff-copy-with-strategy.csv")) {
            var csv = new StatefulBeanToCsvBuilder<Employee>(writer)
                    .withMappingStrategy(strategy)
                    .build();
            csv.write(employees);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void writeObjects(List<Employee> employees) {
        try (Writer writer = new FileWriter("csv-xml-json/src/main/resources/csv/all-staff-copy.csv")) {
            var csv = new StatefulBeanToCsvBuilder<Employee>(writer)
                    .build();
            csv.write(employees);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<Employee> readToObjects() {
        try (CSVReader csvReader = new CSVReader(new FileReader("csv-xml-json/src/main/resources/csv/all-staff.csv"))) {
            var strategy = new ColumnPositionMappingStrategy<Employee>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping("id", "firstName", "lastName", "country", "age");
            var csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            var employees = csv.parse();

            employees.forEach(System.out::println);
            return employees;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return List.of();
    }
}
