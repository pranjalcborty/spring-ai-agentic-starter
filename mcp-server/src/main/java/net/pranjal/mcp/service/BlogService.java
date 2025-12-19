package net.pranjal.mcp.service;

import net.pranjal.mcp.domain.Post;
import net.pranjal.mcp.domain.Writer;
import net.pranjal.mcp.repository.PostRepository;
import net.pranjal.mcp.repository.WriterRepository;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

@Service
public class BlogService {

    final PostRepository postRepository;
    final WriterRepository writerRepository;

    public BlogService(PostRepository postRepository, WriterRepository writerRepository) {
        this.postRepository = postRepository;
        this.writerRepository = writerRepository;
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Writer save(Writer writer) {
        return writerRepository.save(writer);
    }

    @McpTool(
            name = "post-find-all",
            description = "I return all blog posts created by all users.")
    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }

    @McpTool(
            name = "post-find-by-post-id",
            description = "I return a specific blog post if an Id is provided.")
    public Post findById(@McpToolParam Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @McpTool(
            name = "post-find-by-writer-name",
            description = "I return all blog posts created by all writers with the given name.")
    public Iterable<Post> findAllByWriter(@McpToolParam String authorName) {
        return postRepository.findAllByWriter_Name(authorName);
    }
}
