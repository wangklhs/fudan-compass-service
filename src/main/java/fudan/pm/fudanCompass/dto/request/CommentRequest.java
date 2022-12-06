package fudan.pm.fudanCompass.dto.request;

import lombok.Data;

@Data
public class CommentRequest {

    private Long id;
    private Long userId;
    private Integer commentType; //0 article 1 rating
    private String content;

}
