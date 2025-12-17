package net.pranjal.testblogmcp.controller;

import net.pranjal.testblogmcp.domain.Post;
import net.pranjal.testblogmcp.domain.Writer;
import net.pranjal.testblogmcp.service.BlogService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class BlogController {

    final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/post")
    public Iterable<Post> getPosts() {
        return blogService.findAll();
    }

    @GetMapping("/post/{id}")
    public Post getPostById(@PathVariable Long id) {
        return blogService.findById(id);
    }

    @PostMapping("/post")
    public Post createPost(@RequestBody Post post) {
        return blogService.save(post);
    }

    @PostMapping("/writer")
    public Writer createWriter(@RequestBody Writer writer) {
        return blogService.save(writer);
    }
}