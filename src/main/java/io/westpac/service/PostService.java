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
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class PostService {

    @Value("${api.posts.url}")
    private String postUri;

    private final AppDao<Post> appDao;
    private final CommentService commentService;
    private final AppUtils appUtils;

    @Autowired
    public PostService(AppDao<Post> appDao, CommentService commentService, AppUtils appUtils) {
        this.appDao = appDao;
        this.commentService = commentService;
        this.appUtils = appUtils;
    }

    public List<Post> getAllPostsWithFilters(Integer limit, Integer offset, String title) {
        if (!title.isEmpty())
            return searchPostByKeyword(title, limit, offset);

        String uri = appUtils.generateUrlWithPageAttributes(postUri, limit, offset);
        return appDao.getAllAsList(uri, new ParameterizedTypeReference<>() {
        });
    }

    public Post getOnePost(Integer id, Boolean expand) throws NotFoundException {
        String uri = postUri + "/" + id;
        Post foundPost = appDao.getOne(uri, Post.class);
        if (expand) {
            List<Comment> allPostComments = commentService.getAllComments(id);
            foundPost.setComments(allPostComments);
        }
        return foundPost;
    }


    public List<Post> searchPostByKeyword(String keyword, Integer limit, Integer offset) {
        List<Post> allPosts = appDao.getAllAsList(postUri, new ParameterizedTypeReference<>() {
        });

        Predicate<Post> filterPredicate = post -> post.getTitle().toLowerCase().contains(keyword) ||
                post.getBody().toLowerCase().contains(keyword);

        return allPosts.stream()
                .filter(filterPredicate)
                .skip(limit * offset)
                .limit(limit)
                .collect(Collectors.toList());
    }
}
