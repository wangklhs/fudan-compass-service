package fudan.pm.fudanCompass.service;

import fudan.pm.fudanCompass.dto.request.DeleteRequest;
import fudan.pm.fudanCompass.entity.LikeInfo;
import fudan.pm.fudanCompass.repository.ArticleRepository;
import fudan.pm.fudanCompass.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteService {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    RatingRepository ratingRepository;

    @Transactional(rollbackFor = Exception.class)
    public void delete(DeleteRequest request) {
        if (request.getDeleteType() == LikeInfo.ARTICLE_TYPE){
            articleRepository.deleteById(request.getId());
        }else {
            ratingRepository.deleteById(request.getId());
        }
    }
}
