package io.westpac;


import io.westpac.dao.AppDao;
import io.westpac.exception.NotFoundException;
import io.westpac.model.Comment;
import io.westpac.model.Post;
import io.westpac.service.CommentService;
import io.westpac.service.PostService;
import io.westpac.utils.AppUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private AppDao<Post> abstractDao;

    @Mock
    private CommentService commentService;

    @Mock
    private AppUtils appUtils;

    private Post post;

    private Comment comment;


    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(postService, "postUri", "");
        post = new Post(1, "title", "body");
        comment = new Comment(1, "a@b.com", "name", "comment body");
    }


    @Test
    public void getAllPostsWithFilters() {
        Integer limit = 2;
        Integer offset = 0;
        String uri = "";
        String uriWithPageAttributes = "?_limit=2&_start=0";

        when(abstractDao.getAllAsList(uriWithPageAttributes, new ParameterizedTypeReference<>() {
        })).thenReturn(Arrays.asList(post, post));
        when(appUtils.generateUrlWithPageAttributes(uri, limit, offset)).thenCallRealMethod();

        List<Post> allPostsWithFilters = postService.getAllPostsWithFilters(2, 0, "");
        assertThat(allPostsWithFilters.size()).isEqualTo(2);
    }


    @Test
    public void searchPostByKeyword_shouldReturnList() {
        when(abstractDao.getAllAsList("", new ParameterizedTypeReference<List<Post>>() {
        })).thenReturn(Arrays.asList(post, new Post(2, "title", "body"), new Post(3, "title3", "body3")));

        List<Post> searchByTitle = postService.searchPostByKeyword("title", 2, 0);
        assertThat(searchByTitle.size()).isEqualTo(2);
        assertThat(searchByTitle.get(0).getTitle()).isEqualTo("title");
        assertThat(searchByTitle.get(1).getTitle()).isEqualTo("title");

    }


    @Test
    public void getOnePost_shouldReturnPostWithComments() throws NotFoundException {
        when(abstractDao.getOne("/1", Post.class)).thenReturn(post);
        when(commentService.getAllComments(1)).thenReturn(Arrays.asList(comment, comment, comment));
        Post onePost = postService.getOnePost(1, true);

        assertNotNull(onePost);
        assertNotNull(onePost.getComments());
        assertThat(onePost.getComments()).isInstanceOf(List.class);
        assertThat(onePost.getComments().size()).isEqualTo(3);
    }

    @Test
    public void getOnePost_shouldReturnPost() throws NotFoundException {
        when(abstractDao.getOne("/1", Post.class)).thenReturn(post);
        Post onePost = postService.getOnePost(1, false);
        assertNotNull(onePost);
        assertTrue(onePost.getComments().isEmpty());
    }

    @Test
    public void getOnePost_shouldThrowException() throws NotFoundException {
        when(abstractDao.getOne("/1", Post.class)).thenThrow(new NotFoundException(""));
        assertThrows(NotFoundException.class, () -> postService.getOnePost(1, false));
    }

}
