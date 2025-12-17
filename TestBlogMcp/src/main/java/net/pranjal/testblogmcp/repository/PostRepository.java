package net.pranjal.testblogmcp.repository;

import net.pranjal.testblogmcp.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
    Iterable<Post> findAllByWriter_Name(String userName);
}
