package io.westpac.service;

import io.westpac.dao.AppDao;
import io.westpac.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Value("${api.posts.url}")
    private String postsUri;

    private final AppDao<Comment> commentAppAppDao;

    @Autowired
    public CommentService(AppDao<Comment> commentAppAppDao) {
        this.commentAppAppDao = commentAppAppDao;
    }

    public List<Comment> getAllComments(Integer postId) {
        String commentsUri = postsUri + "/" + postId + "/comments";
        return commentAppAppDao.getAllAsList(commentsUri, new ParameterizedTypeReference<List<Comment>>() {
        });
    }

}
