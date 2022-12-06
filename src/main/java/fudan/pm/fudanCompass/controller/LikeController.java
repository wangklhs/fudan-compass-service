package fudan.pm.fudanCompass.controller;

import fudan.pm.fudanCompass.dto.request.LikeRequest;
import fudan.pm.fudanCompass.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    LikeService likeService;

    @PostMapping
    public void like(@RequestBody LikeRequest request) throws Exception {
        likeService.like(request);
    }

}
