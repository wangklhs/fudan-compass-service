package fudan.pm.fudanCompass.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fudan.pm.fudanCompass.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDetailsDto {
    private Long id;
    private String username;
    private String password;
    private String major;
}
