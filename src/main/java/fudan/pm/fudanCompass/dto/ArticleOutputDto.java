package fudan.pm.fudanCompass.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fudan.pm.fudanCompass.entity.Article;
import fudan.pm.fudanCompass.entity.Comment;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleOutputDto {

    private Long id;
    private Long userId;
    private String username;
    private String title;
    private String content;
    private List<String> tags;
    private Long likeNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private List<Comment> comments;

}
