package fudan.pm.fudanCompass.service;

import fudan.pm.fudanCompass.dto.RatingDetailsDto;
import fudan.pm.fudanCompass.dto.request.RatingDetailsRequest;
import fudan.pm.fudanCompass.dto.request.RatingRequest;
import fudan.pm.fudanCompass.entity.LikeInfo;
import fudan.pm.fudanCompass.entity.Rating;
import fudan.pm.fudanCompass.repository.LikeInfoRepository;
import fudan.pm.fudanCompass.repository.RatingRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class RatingService {

    @Autowired
    MapperFacade mapperFacade;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    LikeInfoRepository likeInfoRepository;


    public RatingDetailsDto getDetails(RatingDetailsRequest request){
        return ratingRepository.findById(request.getRatingId()).map(a ->
        {
            RatingDetailsDto detailsDto = mapperFacade.map(a, RatingDetailsDto.class);
            if (ObjectUtils.isEmpty(request.getUserId())){
                return detailsDto;
            }
            LikeInfo likeInfo = likeInfoRepository.findFirstByLikeIdAndLikeTypeAndUserId(
                    request.getRatingId(), LikeInfo.RATING_TYPE, request.getUserId());
            if (!ObjectUtils.isEmpty(likeInfo)){
                switch (likeInfo.getLikeOrFavor()){
                    case LikeInfo.LIKE:
                        detailsDto.setIsLikedByUser(true);
                        break;
                    case LikeInfo.FAVOR:
                        detailsDto.setIsFavouredByUser(true);
                        break;
                    case LikeInfo.LIKE_AND_FAVOR:
                        detailsDto.setIsLikedByUser(true);
                        detailsDto.setIsFavouredByUser(true);
                }
            }
            return detailsDto;
        }).orElse(null);
    }

    public void post(RatingRequest request) {
        Rating rating = mapperFacade.map(request, Rating.class);
        ratingRepository.save(rating);
    }

}
