package fudan.pm.fudanCompass.entity;

import fudan.pm.fudanCompass.dto.request.RatingRequest;
import lombok.Data;
import org.springframework.util.ObjectUtils;

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

    public void update(RatingRequest request){
        if (!ObjectUtils.isEmpty(request.getCourseName()))
            courseName = request.getCourseName();
        if (!ObjectUtils.isEmpty(request.getCourseType()))
            courseType = request.getCourseType();
        if (!ObjectUtils.isEmpty(request.getRelatedMajor()))
            relatedMajor = request.getRelatedMajor();
        if (!ObjectUtils.isEmpty(request.getScore()))
            score = request.getScore();
        if (!ObjectUtils.isEmpty(request.getTitle()))
            title = request.getTitle();
        if (!ObjectUtils.isEmpty(request.getContent()))
            content = request.getContent();
    }

}
