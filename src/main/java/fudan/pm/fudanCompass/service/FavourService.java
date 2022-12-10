package fudan.pm.fudanCompass.service;

import fudan.pm.fudanCompass.dto.request.FavourRequest;
import fudan.pm.fudanCompass.entity.Article;
import fudan.pm.fudanCompass.entity.LikeInfo;
import fudan.pm.fudanCompass.entity.Rating;
import fudan.pm.fudanCompass.repository.ArticleRepository;
import fudan.pm.fudanCompass.repository.LikeInfoRepository;
import fudan.pm.fudanCompass.repository.RatingRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class FavourService {

    @Autowired
    MapperFacade mapperFacade;
    @Autowired
    LikeInfoRepository likeInfoRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    RatingRepository ratingRepository;

    public void favour(FavourRequest request) {
        LikeInfo likeInfo = likeInfoRepository.findFirstByLikeIdAndUserId(request.getId(), request.getUserId());
        if (!ObjectUtils.isEmpty(likeInfo)) {
            likeInfo.favour(request.getIsFavour());
        } else {
            likeInfo = mapperFacade.map(request, LikeInfo.class);
            if (request.getIsFavour()) {
                likeInfo.setLikeOrFavor(LikeInfo.FAVOR);
            }
        }
        likeInfoRepository.save(likeInfo);
    }
}
