package fudan.pm.fudanCompass.dto.request;

import lombok.Data;

@Data
public class FavourRequest {

    private Long id;
    private Integer favourType; //0 article 1 rating
    private Long userId;
    private Boolean isFavour;

}
