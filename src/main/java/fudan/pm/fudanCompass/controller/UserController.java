package fudan.pm.fudanCompass.controller;


import fudan.pm.fudanCompass.Utils.JsonResult;
import fudan.pm.fudanCompass.dto.ArticleOutputDto;
import fudan.pm.fudanCompass.dto.RatingOutputDto;
import fudan.pm.fudanCompass.dto.request.SetUserMajorRequest;
import fudan.pm.fudanCompass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUserFavourArticles")
    public List<ArticleOutputDto> getFavourArticles(@RequestParam Long userId) throws Exception {
        return userService.getFavourArticles(userId);
    }

    @GetMapping("/getUserFavourRatings")
    public List<RatingOutputDto> getFavourRatings(@RequestParam Long userId) throws Exception {
        return userService.getFavourRatings(userId);
    }

    @PostMapping("/setUserMajor")
    public void setUserMajor(@RequestBody SetUserMajorRequest request) throws Exception {
        userService.setUserMajor(request);
    }

    @GetMapping("/getUserMajor")
    public String getUserMajor(@RequestParam Long userId) throws Exception {
        return userService.getUserMajor(userId);
    }

    @GetMapping("/getInfo")
    public JsonResult<?> getInfo(@RequestParam Long userId){
        HashMap hashMap = userService.getInfo(userId);
        return new JsonResult<>(hashMap);
    }
    @PostMapping("/getUserTimeTableByDay")
    public JsonResult<?> getUserTimeTableByDay(@RequestParam String day,@RequestParam Long userId){
        HashMap hashMap = userService.getUserTimeTableByDay(day,userId);
        return new JsonResult<>(hashMap);
    }
//    @PostMapping("setUserTimeTableByDay")
//    public void setUserTimeTableByDay(@RequestParam Long userId, @RequestParam String day, @RequestParam List<String> courses){
//        userService.setUserTimeTableByDay(day,userId,courses);
//    }
}
