package net.pranjal.testblogmcp.repository;

import net.pranjal.testblogmcp.domain.Writer;
import org.springframework.data.repository.CrudRepository;

public interface WriterRepository extends CrudRepository<Writer, Long> {
}
