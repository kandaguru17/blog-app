package io.westpac.dao.impl;

import io.westpac.dao.PostDao;
import io.westpac.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.westpac.utils.AppConstants.ERROR_MSG;
import static io.westpac.utils.AppConstants.POSTS;

@Repository
public class PostDaoImpl implements PostDao {

    private final RestTemplate restTemplate;

    @Autowired
    public PostDaoImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Post> getAllPosts() {
        try {
            ResponseEntity<Post[]> response = restTemplate.getForEntity(POSTS, Post[].class);
            if (response.getBody() != null)
                return Arrays.asList(response.getBody());
            return Collections.emptyList();
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            throw new IllegalStateException(ERROR_MSG);
        }
    }
}
