package fudan.pm.fudanCompass.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;

    @Column(columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "bigint default 0")
    private Long likeNum;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(columnDefinition = "datetime default CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(columnDefinition = "datetime default CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "article_comment",
            joinColumns = {@JoinColumn(name = "comment_id")}, inverseJoinColumns = {@JoinColumn(name = "article_id")})
    private Article article;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "rating_comment",
            joinColumns = {@JoinColumn(name = "comment_id")}, inverseJoinColumns = {@JoinColumn(name = "rating_id")})
    private Rating rating;

}
