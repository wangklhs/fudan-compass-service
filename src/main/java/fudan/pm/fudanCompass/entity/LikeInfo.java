package fudan.pm.fudanCompass.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class LikeInfo {

    public static final int ARTICLE_TYPE = 0;
    public static final int RATING_TYPE = 1;
    public static final int COMMENT_TYPE = 2;
    public static final int LIKE = 0;
    public static final int FAVOR = 1;
    public static final int LIKE_AND_FAVOR = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private Long likeId;
    private Integer type; //0 article 1 rating 2 comment
    private Boolean isCancelled;
    private Integer likeType; //0 like 1 favor 2 like and favor

}
