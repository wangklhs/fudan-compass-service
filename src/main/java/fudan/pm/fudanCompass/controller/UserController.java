package fudan.pm.fudanCompass.controller;


import fudan.pm.fudanCompass.dto.ArticleOutputDto;
import fudan.pm.fudanCompass.dto.RatingOutputDto;
import fudan.pm.fudanCompass.dto.request.SetUserMajorRequest;
import fudan.pm.fudanCompass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/getUserFavourArticles")
    public List<ArticleOutputDto> getFavourArticles(@RequestBody Long id) throws Exception {
        return userService.getFavourArticles(id);
    }

    @PostMapping("/getUserFavourRatings")
    public List<RatingOutputDto> getFavourRatings(@RequestBody Long id) throws Exception {
        return userService.getFavourRatings(id);
    }

    @PostMapping("/setUserMajor")
    public void setUserMajor(@RequestBody SetUserMajorRequest request) throws Exception {
        userService.setUserMajor(request);
    }

    @GetMapping("/getUserMajor")
    public String getUserMajor(@RequestBody Long id) throws Exception {
        return userService.getUserMajor(id);
    }
}
