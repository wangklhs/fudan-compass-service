package fudan.pm.fudanCompass.controller;

import fudan.pm.fudanCompass.dto.SearchArticlesRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @PostMapping("/search")
    public void searchArticles(@RequestBody SearchArticlesRequest request) {

    }

    @GetMapping("/{id}")
    public void getArticleDetails(@PathVariable("id") Long id) {

    }

    @PostMapping
    public void postArticle() {

    }

    @PutMapping("/{id}")
    public void updateArticle(@PathVariable("id") Long id) {

    }

    @GetMapping("/like")
    public void like() {

    }

}
