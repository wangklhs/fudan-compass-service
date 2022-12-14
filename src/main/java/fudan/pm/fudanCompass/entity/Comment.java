package fudan.pm.fudanCompass.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fudan.pm.fudanCompass.FudanCompassApplication;
import fudan.pm.fudanCompass.repository.UserRepository;
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

    private Long likeNum = 0L;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime = LocalDateTime.now();

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

    public String getUsername() {
        UserRepository userRepository = FudanCompassApplication.applicationContext.getBean(UserRepository.class);
        return userRepository.findById(userId).map(User::getUsername).orElse(null);
    }

}
