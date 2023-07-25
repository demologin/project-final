package com.javarush.jira.util;

import lombok.experimental.UtilityClass;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


//TODO p10 (docker-compose). For health check
@UtilityClass
public class HealthCheck {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String SERVER_URL = "http://localhost:8080/view/login";
    private static final int MAX_RETRIES = 10;
    private static final int WAIT_TIME = 5000;
    private static final int HTTP_OK = 200;
    private static final String SERVER_UP_MSG = "Server is up and running!";
    private static final String SERVER_NOT_READY_MSG = "Attempt %d failed. Server is not ready yet. Waiting...";
    private static final String SERVER_NOT_RESPONDING_MSG = "Server is not responding after %d attempts.";

    public static void main(String[] args) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SERVER_URL))
                .build();

        tryConnecting(request);
    }

    private static void tryConnecting(HttpRequest request) {
        for (int retries = 0; retries < MAX_RETRIES; retries++) {
            try {
                HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == HTTP_OK) {
                    System.err.println(SERVER_UP_MSG);
                    System.exit(0);
                }
            } catch (Exception e) {
                System.out.printf((SERVER_NOT_READY_MSG) + "%n", retries + 1);
                try {
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.err.printf((SERVER_NOT_RESPONDING_MSG) + "%n", MAX_RETRIES);
        System.exit(1);
    }
}