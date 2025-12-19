package net.pranjal.mcp.controller;

import net.pranjal.mcp.domain.Post;
import net.pranjal.mcp.domain.Writer;
import net.pranjal.mcp.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class BlogController {

    private static final Logger log = LoggerFactory.getLogger(BlogController.class);

    final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/post")
    public Iterable<Post> getPosts() {
        log.info("Getting posts");
        return blogService.findAll();
    }

    @GetMapping("/post/{id}")
    public Post getPostById(@PathVariable Long id) {
        log.info("Getting post with id {}", id);
        return blogService.findById(id);
    }

    @PostMapping("/post")
    public Post createPost(@RequestBody Post post) {
        log.info("Creating post {}", post);
        return blogService.save(post);
    }

    @PostMapping("/writer")
    public Writer createWriter(@RequestBody Writer writer) {
        log.info("Creating writer {}", writer);
        return blogService.save(writer);
    }
}