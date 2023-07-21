package com.javarush.jira.util;

import lombok.experimental.UtilityClass;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//TODO task 10 (docker-compose). For health check
@UtilityClass
public class HealthCheck {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final int MAX_RETRIES = 10;

    public static void main(String[] args) throws InterruptedException {
        String serverUrl = "http://localhost:8080/view/login";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl))
                .build();

        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    System.out.println("Server is up and running!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Server is not ready yet. Waiting...");
                Thread.sleep(5000); // Wait 5 seconds before trying again
            }
            retries++;
        }

        if (retries == MAX_RETRIES) {
            System.out.println("Server is not responding after " + MAX_RETRIES + " attempts.");
        }
    }
}