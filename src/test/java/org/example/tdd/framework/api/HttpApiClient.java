package org.example.tdd.framework.api;

import lombok.RequiredArgsConstructor;
import org.example.tdd.framework.api.dto.request.HttpApiClientRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HttpApiClient {

    private final WebClient webClient;


    public Mono<String> execute(HttpApiClientRequest httpApiClientRequest) {
        return webClient
                .method(httpApiClientRequest.method())
                .uri(httpApiClientRequest.uri())
                .headers(httpHeaders -> httpHeaders.addAll(httpApiClientRequest.httpHeaders()))
                .bodyValue(httpApiClientRequest.body())
                .exchangeToMono(response -> {
                    if (!response.statusCode().equals(HttpStatus.OK)) {
                        return response.createError();
                    }

                    return response.bodyToMono(String.class);
                });
    }
}
