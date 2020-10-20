package io.westpac.service;

import io.westpac.dao.PostDao;
import io.westpac.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostDao postDao;

    @Autowired
    public PostService(PostDao postDao) {
        this.postDao = postDao;
    }

    public List<Post> getAllPosts() {
        return postDao.getAllPosts();
    }

}
