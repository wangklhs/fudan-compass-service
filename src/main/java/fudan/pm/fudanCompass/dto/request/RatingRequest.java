package fudan.pm.fudanCompass.dto.request;

import lombok.Data;

@Data
public class RatingRequest {

    private Long userId;
    private String courseName;
    private String courseType;
    private String relatedMajor;
    private Integer score;
    private String title;
    private String content;

}
