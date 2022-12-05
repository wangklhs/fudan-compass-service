package fudan.pm.fudanCompass.service;

import fudan.pm.fudanCompass.dto.ArticleDetailsDto;
import fudan.pm.fudanCompass.dto.ArticleOutputDto;
import fudan.pm.fudanCompass.dto.request.ArticleRequest;
import fudan.pm.fudanCompass.dto.request.SearchArticlesRequest;
import fudan.pm.fudanCompass.entity.Article;
import fudan.pm.fudanCompass.repository.ArticleRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    MapperFacade mapperFacade;
    @Autowired
    ArticleRepository articleRepository;

    public Page<ArticleOutputDto> search(SearchArticlesRequest request) {
        Specification<Article> specification = (root, query, cb) -> {
            List<Predicate> predicates = new LinkedList<>();
            if (!ObjectUtils.isEmpty(request.getSearchContent())) {
                Predicate[] arr = new Predicate[]{
                        cb.like(root.get("title"), "%" + request.getSearchContent() + "%"),
                        cb.like(root.get("content"), "%" + request.getSearchContent() + "%")
                };
                predicates.add(cb.or(arr));
            }
            if (!ObjectUtils.isEmpty(request.getTags())) {
                int size = request.getTags().size();
                Predicate[] arr = new Predicate[size];
                for (int i = 0; i < size; i++) {
                    arr[i] = cb.like(root.get("tags"), "%" + request.getTags().get(i) + "%");
                }
                predicates.add(cb.or(arr));
            }
            Predicate[] arr = new Predicate[predicates.size()];
            query.where(cb.and(predicates.toArray(arr)));
            if (!ObjectUtils.isEmpty(request.getOrderBy()) && request.getOrderBy() == 1)
                query.orderBy(cb.desc(root.get("likeNum")), cb.desc(root.get("updateTime")));
            else
                query.orderBy(cb.desc(root.get("updateTime")));
            return query.getRestriction();
        };

        return articleRepository.findAll(specification, PageRequest.of(request.getPageNo(), request.getPageSize()))
                .map(a -> mapperFacade.map(a, ArticleOutputDto.class));
    }

    public ArticleDetailsDto getDetails(Long id) {
        return articleRepository.findById(id).map(a -> mapperFacade.map(a, ArticleDetailsDto.class)).orElse(null);
    }

    public void post(ArticleRequest request) {
        Article article = mapperFacade.map(request, Article.class);
        article.setLikeNum(0L);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        articleRepository.save(article);
    }

    public void update(Long id, ArticleRequest request) {
        Article article = mapperFacade.map(request, Article.class);
        article.setId(id);
        article.setUpdateTime(LocalDateTime.now());
        articleRepository.save(article);
    }


}
