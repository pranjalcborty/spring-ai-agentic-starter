package net.pranjal.mcp.domain;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Entity
public class Post implements Serializable {

    @Serial
    private static final long serialVersionUID = 8739892075746926464L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(max = 512)
    private String post;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Writer writer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }
}
