package fudan.pm.fudanCompass.dto.request;

import lombok.Data;

@Data
public class CommentRequest {

    private Long id;
    private Long userId;
    private Integer commentType;
    private String content;

}
