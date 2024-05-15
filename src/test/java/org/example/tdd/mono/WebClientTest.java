package org.example.tdd.mono;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.tdd.framework.api.HttpApiClient;
import org.example.tdd.framework.api.dto.request.HttpApiClientRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.io.IOException;

@SpringBootTest
public class WebClientTest {

    @Autowired
    private HttpApiClient httpApiClient;

    private MockWebServer mockWebServer;
    private String mockWebServerUrl;


    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        mockWebServerUrl = mockWebServer.url("/api/test").toString();
    }

    private final String mockScrap = "{\n"
            + "  \"resultCode\": 100,\n"
            + "  \"resultMessage\": \"성공입니다\"\n"
            + "}\n";

    @Test
    void webClientTest() throws IOException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mockScrap)
                .addHeader("Content-Type", "application/json"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Mono<String> apiMono = httpApiClient.execute(new HttpApiClientRequest(
                "https://test.com",
                mockWebServerUrl,
                headers,
                HttpMethod.POST,
                ""
        ));

        String result = apiMono
                .doOnError(throwable -> System.out.println("throwable = " + throwable))
                .block();

        System.out.println("result = " + result);
    }


    @AfterEach
    void terminate() throws IOException {
        mockWebServer.shutdown();
    }

}
