package io.westpac.controller;

import io.westpac.exception.NotFoundException;
import io.westpac.model.Comment;
import io.westpac.model.Post;
import io.westpac.service.CommentService;
import io.westpac.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class AppController {

    private final PostService postService;
    private final CommentService commentsService;

    @Autowired
    public AppController(PostService postService, CommentService commentsService) {
        this.postService = postService;
        this.commentsService = commentsService;
    }

    @GetMapping("")
    public ResponseEntity<List<Post>> getAllBlogPosts(@RequestParam(required = false, value = "limit") Optional<Integer> limit,
                                                      @RequestParam(required = false, value = "offset") Optional<Integer> offset,
                                                      @RequestParam(required = false, value = "keyword") Optional<String> keyword) {
        List<Post> allPosts = postService
                .getAllPostsWithFilters(limit.orElse(10), offset.orElse(0), keyword.orElse(""));
        return ResponseEntity.ok(allPosts);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Post> getOneBlogPost(@PathVariable Integer id, @RequestParam(required = false, value = "expand") Optional<Boolean> expand) throws NotFoundException {
        Post foundPost = postService.getOnePost(id, expand.orElse(false));
        return ResponseEntity.ok(foundPost);
    }


    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable Integer id) throws NotFoundException {
        List<Comment> allComments = commentsService.getAllComments(id);
        return ResponseEntity.ok(allComments);
    }
}
