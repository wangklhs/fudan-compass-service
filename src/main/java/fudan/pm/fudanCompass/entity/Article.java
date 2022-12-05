package fudan.pm.fudanCompass.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String title;

    @Column(columnDefinition = "text")
    private String content;

    private Long likeNum;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @OneToMany
    @JoinTable(name = "article_tag",
            joinColumns = {@JoinColumn(name = "article_id")}, inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags;

    @OneToMany
    @JoinTable(name = "article_comment",
            joinColumns = {@JoinColumn(name = "article_id")}, inverseJoinColumns = {@JoinColumn(name = "comment_id")})
    private List<Comment> comments;

}
