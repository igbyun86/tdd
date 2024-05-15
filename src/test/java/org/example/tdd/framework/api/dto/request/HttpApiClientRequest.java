package org.example.tdd.framework.api.dto.request;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public record HttpApiClientRequest (
        String host,
        String uri,
        HttpHeaders httpHeaders,
        HttpMethod method,
        Object body
){

}
