package fudan.pm.fudanCompass.service;

import fudan.pm.fudanCompass.dto.request.LikeRequest;
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
public class LikeService {

    @Autowired
    MapperFacade mapperFacade;
    @Autowired
    LikeInfoRepository likeInfoRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    RatingRepository ratingRepository;

    @Transactional(rollbackFor = Exception.class)
    public void like(LikeRequest request) throws Exception {
        if (ObjectUtils.isEmpty(request.getLikeType()))
            throw new Exception();
        LikeInfo likeInfo = likeInfoRepository.findFirstByLikeIdAndLikeTypeAndUserId(request.getId(), request.getLikeType(), request.getUserId());
        boolean needAdd;
        boolean needReduce = false;
        if (!ObjectUtils.isEmpty(likeInfo)) {
            boolean temp = likeInfo.like(request.getIsLike());
            needAdd = request.getIsLike() && temp;
            needReduce = !request.getIsLike() && temp;
        } else {
            likeInfo = mapperFacade.map(request, LikeInfo.class);
            if (needAdd = request.getIsLike())
                likeInfo.setLikeOrFavor(LikeInfo.LIKE);
        }
        int inc = (needAdd || needReduce) ? (needAdd ? 1 : -1) : 0;
        updateLikeNum(request.getId(), request.getLikeType(), inc);
        likeInfoRepository.save(likeInfo);
    }

    private void updateLikeNum(Long id, int likeType, int inc) throws Exception {
        if (inc != 0) {
            if (likeType == LikeInfo.ARTICLE_TYPE) {
                Article a = articleRepository.findById(id).orElseThrow(Exception::new);
                a.setLikeNum(a.getLikeNum() + inc);
                articleRepository.save(a);
            } else {
                Rating r = ratingRepository.findById(id).orElseThrow(Exception::new);
                r.setLikeNum(r.getLikeNum() + inc);
                ratingRepository.save(r);
            }
        }
    }

}
