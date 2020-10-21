package io.westpac.controller;

import io.westpac.exception.NotFoundException;
import io.westpac.model.Post;
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

    @Autowired
    public AppController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public ResponseEntity<List<Post>> getAllBlogPosts(@RequestParam(required = false, value = "limit") Optional<Integer> limit,
                                                      @RequestParam(required = false, value = "offset") Optional<Integer> offset) {
        List<Post> allPosts = postService
                .getAllPosts(limit.orElse(10), offset.orElse(0));
        return ResponseEntity.ok(allPosts);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Post> getOneBlogPost(@PathVariable Integer id, @RequestParam(required = false, value = "expand") Optional<Boolean> expand) throws NotFoundException {
        Post foundPost = postService.getOnePost(id, expand.orElse(false));
        return ResponseEntity.ok(foundPost);
    }
}
