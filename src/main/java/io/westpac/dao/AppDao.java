package io.westpac.dao;

import io.westpac.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Repository
public class AppDao<T> {

    private final RestTemplate restTemplate;

    @Autowired
    public AppDao(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<T> getAllAsList(String uri, ParameterizedTypeReference<List<T>> responseType) {
        try {
            ResponseEntity<List<T>> response = restTemplate
                    .exchange(uri, HttpMethod.GET, null, responseType);

            if (response.getBody() != null)
                return response.getBody();

            return Collections.emptyList();
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public T getOne(String uri, Class<T> t) throws NotFoundException {
        try {
            return restTemplate.getForObject(uri, t);
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
