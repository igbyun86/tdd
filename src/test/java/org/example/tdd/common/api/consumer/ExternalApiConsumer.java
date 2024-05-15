package org.example.tdd.common.api.consumer;

import lombok.RequiredArgsConstructor;
import org.example.tdd.framework.api.HttpApiClient;
import org.example.tdd.framework.api.dto.request.HttpApiClientRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExternalApiConsumer {

    private final HttpApiClient httpApiClient;


    public String getData(String uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return httpApiClient.execute(new HttpApiClientRequest(
                "https://test.com",
                        uri,
                        headers,
                        HttpMethod.POST,
                        ""
                ))
                .onErrorReturn("ERROR")
                .block();
    }


}
