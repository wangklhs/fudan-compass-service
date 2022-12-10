package fudan.pm.fudanCompass.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fudan.pm.fudanCompass.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RatingOutputDto {

    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String courseName;
    private String courseType;
    private String relatedMajor;
    private Integer score;
    private Long likeNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private List<Comment> comments;
}
