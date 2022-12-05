package fudan.pm.fudanCompass.dto.request;

import lombok.Data;

@Data
public class CommentRequest {

    private Long articleId;
    private Long userId;
    private Long parentId;
    private String content;

}
