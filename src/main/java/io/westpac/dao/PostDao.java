package io.westpac.dao;

import io.westpac.model.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao {
    List<Post> getAllPosts();
}


