package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Main {
    public record Post(int userId, int id, String title, String body) {}

    public static void main(String[] args) throws IOException {
        // создание клиента для работы по http
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("My IntelliJ IDEA Service")
                .setDefaultRequestConfig(
                        RequestConfig.custom()
                                .setConnectTimeout(5_000)
                                .setSocketTimeout(30_000)
                                .setRedirectsEnabled(false)
                                .build()
                )
                .build();

        // создание объекта запроса с произвольными заголовками
        HttpGet request = new HttpGet("https://jsonplaceholder.typicode.com/posts");
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        // отправка запроса
        CloseableHttpResponse response = httpClient.execute(request);

        // вывод полученных заголовков
        System.out.println("====ЗАГОЛОВКИ====");
        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

        // чтение тела ответа
        String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("====ТЕЛО====");
        System.out.println(body);

        // преобразование в объекты
        response = httpClient.execute(request);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Post> posts = objectMapper.readValue(
                response.getEntity().getContent(),
                new TypeReference<>() {}
        );

        posts.forEach(System.out::println);
    }
}