package fudan.pm.fudanCompass.controller;

import fudan.pm.fudanCompass.dto.ArticleDetailsDto;
import fudan.pm.fudanCompass.dto.ArticleOutputDto;
import fudan.pm.fudanCompass.dto.request.ArticleDetailsRequest;
import fudan.pm.fudanCompass.dto.request.ArticleRequest;
import fudan.pm.fudanCompass.dto.request.SearchArticlesRequest;
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

    @PostMapping("/detail")
    public ArticleDetailsDto getDetails(@RequestBody ArticleDetailsRequest request) {
        return articleService.getDetails(request);
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

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable("id") Long id) {
        articleService.delete(id);
    }

}
