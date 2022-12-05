package fudan.pm.fudanCompass.entity;

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

    private Long likeNum;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
