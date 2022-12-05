package fudan.pm.fudanCompass.controller;

import fudan.pm.fudanCompass.dto.request.CommentRequest;
import fudan.pm.fudanCompass.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping
    public void postComment(@RequestBody CommentRequest request) throws Exception {
        commentService.post(request);
    }

}
