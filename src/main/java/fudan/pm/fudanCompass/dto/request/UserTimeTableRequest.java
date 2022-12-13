package fudan.pm.fudanCompass.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UserTimeTableRequest {
    String day;
    Long userId;
    List<String> courses;
}
