package fudan.pm.fudanCompass.dto.request;

import lombok.Data;

@Data
public class SetUserMajorRequest {

    private Long userId;
    private String major;
}
