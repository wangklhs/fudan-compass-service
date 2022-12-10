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

    @Transactional(rollbackFor = Exception.class)
    public void favour(FavourRequest request) throws Exception {
        LikeInfo likeInfo = likeInfoRepository.findFirstByLikeIdAndUserId(request.getId(), request.getUserId());
        boolean needFavour;
        boolean needNotFavour = false;
        if (!ObjectUtils.isEmpty(likeInfo)){
            boolean temp = likeInfo.favour(request.getIsFavour());
            needFavour = request.getIsFavour() && temp;
            needNotFavour = !request.getIsFavour() && temp;
        }else {
            likeInfo = mapperFacade.map(request, LikeInfo.class);
            if (needFavour = request.getIsFavour()){
                likeInfo.setLikeOrFavor(LikeInfo.FAVOR);
            }
        }

        if (needFavour || needNotFavour) {
            if (request.getFavourType() == LikeInfo.ARTICLE_TYPE){
                Article a = articleRepository.findById(request.getId()).orElseThrow(Exception::new);
                a.setFavoured(needFavour);
                articleRepository.save(a);
            }else {
                Rating r = ratingRepository.findById(request.getId()).orElseThrow(Exception::new);
                r.setFavoured(needFavour);
                ratingRepository.save(r);
            }
        }
    }
}
