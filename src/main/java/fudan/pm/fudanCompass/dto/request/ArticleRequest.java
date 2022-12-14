package fudan.pm.fudanCompass.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ArticleRequest {

    private Long userId;
    private String title;
    private String content;
    private List<String> tags;

}
