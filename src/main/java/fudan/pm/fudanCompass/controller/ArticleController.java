package fudan.pm.fudanCompass.controller;

import fudan.pm.fudanCompass.dto.ArticleOutputDto;
import fudan.pm.fudanCompass.dto.ArticleRequest;
import fudan.pm.fudanCompass.dto.SearchArticlesRequest;
import fudan.pm.fudanCompass.entity.Article;
import fudan.pm.fudanCompass.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping("/search")
    public Page<ArticleOutputDto> search(@RequestBody SearchArticlesRequest request) {
        if (ObjectUtils.isEmpty(request.getPageNo()) || ObjectUtils.isEmpty(request.getPageSize()))
            return null; //异常处理
        return articleService.search(request);
    }

    @GetMapping("/{id}")
    public ArticleOutputDto getDetails(@PathVariable("id") Long id) {
        return articleService.getDetails(id);
    }

    @PostMapping
    public void postArticle(@RequestBody ArticleRequest request) {
        articleService.post(request);
    }

    @PutMapping("/{id}")
    public void updateArticle(@PathVariable("id") Long id,
                              @RequestBody ArticleRequest request) {
        articleService.update(id, request);
    }

    @GetMapping("/like")
    public void like() {

    }

}
