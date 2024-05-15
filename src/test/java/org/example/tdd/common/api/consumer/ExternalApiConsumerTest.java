package org.example.tdd.common.api.consumer;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ExternalApiConsumerTest {

    @Autowired
    ExternalApiConsumer externalApiConsumer;

    private MockWebServer mockWebServer;
    private String mockWebServerUrl;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        mockWebServerUrl = mockWebServer.url("/api/data").toString();
    }

    private final String mockScrap = "{\n"
            + "  \"resultCode\": 100,\n"
            + "  \"resultMessage\": \"성공입니다\"\n"
            + "}\n";

    @Test
    @DisplayName("wrapper api class test")
    void test() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mockScrap)
                .addHeader("Content-Type", "application/json"));

        String data = externalApiConsumer.getData(mockWebServerUrl);
        System.out.println("data = " + data);
    }

}