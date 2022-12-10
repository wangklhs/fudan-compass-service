package fudan.pm.fudanCompass.service;

import fudan.pm.fudanCompass.dto.request.DeleteRequest;
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
    public void deleteArticle(DeleteRequest request) {
        articleRepository.deleteById(request.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRating(DeleteRequest request) {
        ratingRepository.deleteById(request.getId());
    }
}
