package fudan.pm.fudanCompass.controller;


import fudan.pm.fudanCompass.dto.ArticleOutputDto;
import fudan.pm.fudanCompass.dto.RatingOutputDto;
import fudan.pm.fudanCompass.dto.request.SetUserMajorRequest;
import fudan.pm.fudanCompass.dto.request.UserFavourArticlesRequest;
import fudan.pm.fudanCompass.dto.request.UserFavourRatingsRequest;
import fudan.pm.fudanCompass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/getUserFavourArticles")
    public List<ArticleOutputDto> getFavourArticles(@RequestBody UserFavourArticlesRequest request) throws Exception {
        return userService.getFavourArticles(request);
    }

    @PostMapping("/getUserFavourRatings")
    public List<RatingOutputDto> getFavourRatings(@RequestBody UserFavourRatingsRequest request) throws Exception {
        return userService.getFavourRatings(request);
    }

    @PostMapping("/setUserMajor")
    public void setUserMajor(@RequestBody SetUserMajorRequest request) throws Exception {
        userService.setUserMajor(request);
    }
}
