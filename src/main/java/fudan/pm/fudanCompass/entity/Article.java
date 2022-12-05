package fudan.pm.fudanCompass.entity;

import fudan.pm.fudanCompass.dto.ArticleRequest;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private String tags;
    private Long likeNum;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @OneToMany
    @JoinTable(name = "article_comment",
            joinColumns = {@JoinColumn(name = "article_id")}, inverseJoinColumns = {@JoinColumn(name = "comment_id")})
    private List<Comment> comments;

}
