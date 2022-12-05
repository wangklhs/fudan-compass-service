package fudan.pm.fudanCompass.service;

import fudan.pm.fudanCompass.dto.request.CommentRequest;
import fudan.pm.fudanCompass.entity.Article;
import fudan.pm.fudanCompass.entity.Comment;
import fudan.pm.fudanCompass.repository.ArticleRepository;
import fudan.pm.fudanCompass.repository.CommentRepository;
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

    public void post(CommentRequest request) throws Exception {
        Article article = articleRepository.findById(request.getArticleId()).orElseThrow(Exception::new); //异常处理
        if (!ObjectUtils.isEmpty(request.getParentId()) && !commentRepository.existsById(request.getParentId()))
            throw new Exception(); //异常处理
        Comment comment = mapperFacade.map(request, Comment.class);
        comment.setArticle(article);
        commentRepository.save(comment);
    }

}
