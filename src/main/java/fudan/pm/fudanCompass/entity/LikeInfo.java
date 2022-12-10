package fudan.pm.fudanCompass.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class LikeInfo {

    public static final int ARTICLE_TYPE = 0;
    public static final int RATING_TYPE = 1;
    public static final int COMMENT_TYPE = 2;
    public static final int NOTHING = -1;
    public static final int LIKE = 0;
    public static final int FAVOR = 1;
    public static final int LIKE_AND_FAVOR = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;
    private Long likeId;
    private Integer likeType; //0 article 1 rating 2 comment
    private Integer likeOrFavor = NOTHING; //-1 nothing 0 like 1 favor 2 like and favor

    public boolean like(boolean isLike) {
        if (isLike) {
            switch (likeOrFavor) {
                case NOTHING:
                    likeOrFavor = LIKE;
                    return true;
                case FAVOR:
                    likeOrFavor = LIKE_AND_FAVOR;
                    return true;
            }
        } else {
            switch (likeOrFavor) {
                case LIKE:
                    likeOrFavor = NOTHING;
                    return true;
                case LIKE_AND_FAVOR:
                    likeOrFavor = FAVOR;
                    return true;
            }
        }
        return false;
    }

    public boolean favour(boolean isFavour) {
        if (isFavour) {
            switch (likeOrFavor) {
                case NOTHING:
                    likeOrFavor = FAVOR;
                    return true;
                case LIKE:
                    likeOrFavor = LIKE_AND_FAVOR;
                    return true;
            }
        } else {
            switch (likeOrFavor) {
                case FAVOR:
                    likeOrFavor = NOTHING;
                    return true;
                case LIKE_AND_FAVOR:
                    likeOrFavor = LIKE;
                    return true;
            }
        }
        return false;
    }
}
