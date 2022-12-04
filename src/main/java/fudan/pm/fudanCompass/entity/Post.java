package fudan.pm.fudanCompass.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long posterId;

    @Column(columnDefinition = "text")
    private String content;


    @OneToMany
    @JoinTable(name = "post_comment",
            joinColumns = {@JoinColumn(name = "post_id")}, inverseJoinColumns = {@JoinColumn(name = "comment_id")})
    private List<Comment> comments;

}
