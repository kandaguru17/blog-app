package io.westpac.dao;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class AppDao<T> extends AbstractDao<T> {

    public AppDao(RestTemplate restTemplate) {
        super(restTemplate);
    }
}
