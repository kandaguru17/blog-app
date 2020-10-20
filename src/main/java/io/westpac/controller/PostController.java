package io.westpac.controller;

import io.westpac.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @GetMapping
    public ResponseEntity<List<Post>> getAllBlogPosts() {
        return null;
    }

}
