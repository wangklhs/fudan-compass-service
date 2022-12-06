package fudan.pm.fudanCompass.dto.request;

import lombok.Data;

@Data
public class LikeRequest {

    private Long id;
    private Integer likeType;
    private Long userId;
    private Boolean isLike;

}
