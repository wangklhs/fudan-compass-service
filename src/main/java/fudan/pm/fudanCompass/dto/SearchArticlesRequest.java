package fudan.pm.fudanCompass.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchArticlesRequest {

    private String searchContent;
    private int orderBy;
    private List<String> tags;
    private int pageNo;
    private int pageSize;

}
