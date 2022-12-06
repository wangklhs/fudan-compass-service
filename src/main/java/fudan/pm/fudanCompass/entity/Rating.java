package fudan.pm.fudanCompass.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private String title;
    private String tags;

    @Column(columnDefinition = "text")
    private String content;

    private Long likeNum = 0L;
    private LocalDateTime createTime = LocalDateTime.now();
    private LocalDateTime updateTime = LocalDateTime.now();

    private String courseName;
    private String courseType;
    private String relatedMajor;
    private Integer score;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "rating_comment",
            joinColumns = {@JoinColumn(name = "rating_id")}, inverseJoinColumns = {@JoinColumn(name = "comment_id")})
    private List<Comment> comments;

}
