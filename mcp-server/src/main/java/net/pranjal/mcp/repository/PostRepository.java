package net.pranjal.mcp.repository;

import net.pranjal.mcp.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
    Iterable<Post> findAllByWriter_Name(String userName);
}
