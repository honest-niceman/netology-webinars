package com.netology.json;

import com.google.gson.*;
import com.netology.entity.Employee;

import java.io.*;

public class JsonExamples {
    public static void main(String[] args) {
        read();
        write();
        gsonWrite();
        gsonRead();
    }

    private static void gsonRead() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String json = "{\"id\":112,\"firstName\":\"Petr\",\"lastName\":\"Petrov\",\"country\":\"Russia\",\"age\":33}";

        Employee employee = gson.fromJson(json, Employee.class);

        System.out.println(employee);
    }

    private static void gsonWrite() {
        Employee employee = new Employee();
        employee.setAge(33);
        employee.setCountry("Russia");
        employee.setFirstName("Petr");
        employee.setLastName("Petrov");
        employee.setId(112L);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder/*.setPrettyPrinting()*/.create();
        String json = gson.toJson(employee);

        try (FileWriter fileWriter = new FileWriter("csv-xml-json/src/main/resources/json/gson-data.json")) {
            fileWriter.write(json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void write() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("name", new JsonPrimitive("John Doe"));
        JsonArray elements = new JsonArray(2);
        elements.add("129318");
        elements.add("129312");
        jsonObject.add("phoneNumbers", elements);
        jsonObject.addProperty("active", true);

        try (FileWriter fileWriter = new FileWriter("csv-xml-json/src/main/resources/json/new-data.json")) {
            fileWriter.write(jsonObject.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void read() {
        try {
            Object obj = JsonParser.parseReader(new FileReader("csv-xml-json/src/main/resources/json/data.json"));

            JsonObject jsonObject = (JsonObject) obj;
            System.out.println(jsonObject);

            JsonElement lastName = jsonObject.get("lastName");
            System.out.println(lastName.toString());

            JsonArray array = jsonObject.getAsJsonArray("phoneNumbers");
            System.out.println(array);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
