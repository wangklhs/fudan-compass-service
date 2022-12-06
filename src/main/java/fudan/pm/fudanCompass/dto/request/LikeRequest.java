package fudan.pm.fudanCompass.dto.request;

import lombok.Data;

@Data
public class LikeRequest {

    private Long id;
    private Integer likeType; //0 article 1 rating
    private Long userId;
    private Boolean isLike = true;

}
