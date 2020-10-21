package io.westpac.service;

import io.westpac.dao.AppDao;
import io.westpac.exception.NotFoundException;
import io.westpac.model.Comment;
import io.westpac.model.Post;
import io.westpac.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostService {

    @Value("${api.posts.url}")
    private String postUri;
    private final AppDao<Post> postAppDao;
    private final CommentService commentService;
    private final AppUtils appUtils;

    @Autowired
    public PostService(AppDao<Post> postAppDao, CommentService commentService, AppUtils appUtils) {
        this.postAppDao = postAppDao;
        this.commentService = commentService;
        this.appUtils = appUtils;
    }

    public List<Post> getAllPosts(Integer limit, Integer offset) {
        if (limit != null && offset != null)
            postUri = appUtils.generateUrlWithPageAttributes(postUri, limit, offset);
        return postAppDao.getAllAsList(postUri, new ParameterizedTypeReference<>() {
        });
    }

    public Post getOnePost(Integer id, Boolean expand) throws NotFoundException {
        String uri = postUri + "/" + id;
        Post foundPost = postAppDao.getOne(uri, Post.class);
        if (expand) {
            List<Comment> allPostComments = commentService.getAllComments(id);
            foundPost.setComments(allPostComments);
        }
        return foundPost;
    }
}
