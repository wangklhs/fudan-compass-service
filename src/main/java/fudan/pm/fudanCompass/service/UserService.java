package fudan.pm.fudanCompass.service;

import fudan.pm.fudanCompass.dto.ArticleOutputDto;
import fudan.pm.fudanCompass.dto.RatingOutputDto;
import fudan.pm.fudanCompass.dto.request.SetUserMajorRequest;
import fudan.pm.fudanCompass.entity.Article;
import fudan.pm.fudanCompass.entity.LikeInfo;
import fudan.pm.fudanCompass.entity.Rating;
import fudan.pm.fudanCompass.entity.User;
import fudan.pm.fudanCompass.repository.ArticleRepository;
import fudan.pm.fudanCompass.repository.LikeInfoRepository;
import fudan.pm.fudanCompass.repository.RatingRepository;
import fudan.pm.fudanCompass.repository.UserRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    MapperFacade mapperFacade;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    LikeInfoRepository likeInfoRepository;
    @Autowired
    UserRepository userRepository;

    public List<ArticleOutputDto> getFavourArticles(Long id) throws Exception {
        List<ArticleOutputDto> articleOutputDtos = new LinkedList<>();
        List<LikeInfo> likeInfos = likeInfoRepository.findAll();
        for (LikeInfo likeinfo: likeInfos){
            if ((likeinfo.getUserId() == id)
                    && (likeinfo.getLikeType() == LikeInfo.ARTICLE_TYPE)
                    && ((likeinfo.getLikeOrFavor() == LikeInfo.FAVOR) || (likeinfo.getLikeOrFavor() == LikeInfo.LIKE_AND_FAVOR))) {
                Article a = articleRepository.findById(likeinfo.getLikeId()).orElseThrow(Exception::new);
                ArticleOutputDto ao = mapperFacade.map(a, ArticleOutputDto.class);
                articleOutputDtos.add(ao);
            }
        }
        return articleOutputDtos;
    }

    public List<RatingOutputDto> getFavourRatings(Long id) throws Exception {
        List<RatingOutputDto> ratingOutputDtos = new LinkedList<>();
        List<LikeInfo> likeInfos = likeInfoRepository.findAll();
        for (LikeInfo likeinfo: likeInfos){
            if ((likeinfo.getUserId() == id)
                    && (likeinfo.getLikeType() == LikeInfo.RATING_TYPE)
                    && ((likeinfo.getLikeOrFavor() == LikeInfo.FAVOR) || (likeinfo.getLikeOrFavor() == LikeInfo.LIKE_AND_FAVOR))) {
                Rating r = ratingRepository.findById(likeinfo.getLikeId()).orElseThrow(Exception::new);
                RatingOutputDto ro = mapperFacade.map(r, RatingOutputDto.class);
                ratingOutputDtos.add(ro);
            }
        }
        return ratingOutputDtos;
    }

    public void setUserMajor(SetUserMajorRequest request) throws Exception {
        User user = userRepository.findById(request.getUserId()).orElseThrow(Exception::new);
        user.setMajor(request.getMajor());
        userRepository.save(user);
    }

    public String getUserMajor(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(Exception::new);
        return user.getMajor();
    }
}
