package io.westpac;

import io.westpac.dao.AppDao;
import io.westpac.exception.NotFoundException;
import io.westpac.model.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppDaoTest {

    @InjectMocks
    AppDao<Post> appDao;

    @Mock
    RestTemplate restTemplate;

    @Test
    public void getAllAsList_shouldReturnList() {
        when(restTemplate.exchange(eq(""), any(HttpMethod.class), eq(null), any(ParameterizedTypeReference.class))).thenReturn(ResponseEntity.ok().body(Arrays.asList(new Post(1, "title", "body"), new Post(2, "title1", "body2"))));
        List<Post> allAsList = appDao.getAllAsList("", createParameterizedTypeReference());
        assertThat(allAsList.size()).isEqualTo(2);
    }

    @Test
    public void getAllAsList_shouldReturnEmptyList() {
        when(restTemplate.exchange(eq(""), any(HttpMethod.class), eq(null), any(ParameterizedTypeReference.class))).thenReturn(ResponseEntity.ok().body(null));
        List<Post> allAsList = appDao.getAllAsList("", createParameterizedTypeReference());
        assertTrue(allAsList.isEmpty());
    }


    @Test
    public void getAllAsList_shouldThrowException() {
        when(restTemplate.exchange(eq(""), any(HttpMethod.class), eq(null), any(ParameterizedTypeReference.class))).thenThrow(new HttpServerErrorException(HttpStatus.BAD_REQUEST));
        assertThrows(IllegalStateException.class, () -> appDao.getAllAsList("", createParameterizedTypeReference()));
    }

    @Test
    public void getOne_shouldThrowException() {
        when(restTemplate.getForObject(eq(""), any(Class.class))).thenThrow(new HttpServerErrorException(HttpStatus.BAD_REQUEST));
        assertThrows(NotFoundException.class, () -> appDao.getOne("", Post.class));
    }

    @Test
    public void getOne_shouldReturnEntity() throws NotFoundException {
        String body = "body";
        String title = "title";
        int id = 1;
        when(restTemplate.getForObject(eq(""), any(Class.class))).thenReturn(new Post(id, title, body));
        Post one = appDao.getOne("", Post.class);
        assertNotNull(one);
        assertThat(one.getBody()).isEqualTo(body);
        assertThat(one.getTitle()).isEqualTo(title);
        assertThat(one.getId()).isEqualTo(id);

    }

    public ParameterizedTypeReference<List<Post>> createParameterizedTypeReference() {
        return new ParameterizedTypeReference<>() {
        };
    }

}
