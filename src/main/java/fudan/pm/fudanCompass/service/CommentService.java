package fudan.pm.fudanCompass.service;

import fudan.pm.fudanCompass.dto.request.CommentRequest;
import fudan.pm.fudanCompass.entity.Article;
import fudan.pm.fudanCompass.entity.Comment;
import fudan.pm.fudanCompass.entity.Rating;
import fudan.pm.fudanCompass.repository.ArticleRepository;
import fudan.pm.fudanCompass.repository.CommentRepository;
import fudan.pm.fudanCompass.repository.RatingRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Service
public class CommentService {
    @Autowired
    MapperFacade mapperFacade;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    RatingRepository ratingRepository;

    public void post(CommentRequest request) throws Exception {
        Comment comment = mapperFacade.map(request, Comment.class);
        comment.setId(null);
        if (ObjectUtils.isEmpty(request.getCommentType()) || request.getCommentType() == 0) {
            Article article = articleRepository.findById(request.getId()).orElseThrow(Exception::new); //异常处理
            comment.setArticle(article);
        } else {
            Rating rating = ratingRepository.findById(request.getId()).orElseThrow(Exception::new); //异常处理
            comment.setRating(rating);
        }
        commentRepository.save(comment);
    }

}
