package fudan.pm.fudanCompass.dto.request;

import lombok.Data;

@Data
public class DeleteRequest {

    private Long id;
    private Integer deleteType; //0 article 1 rating
    private Long userId;

}
