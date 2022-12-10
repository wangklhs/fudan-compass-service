package fudan.pm.fudanCompass.dto.request;

import lombok.Data;

@Data
public class SearchRatingsRequest {

    private int orderBy;
    private String searchContent;
    private String courseName;
    private String courseType;
    private String relatedMajor;
    private int pageNo;
    private int pageSize;

}
