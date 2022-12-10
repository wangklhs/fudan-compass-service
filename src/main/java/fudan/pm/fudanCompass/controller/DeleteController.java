package fudan.pm.fudanCompass.controller;

import fudan.pm.fudanCompass.dto.request.DeleteRequest;
import fudan.pm.fudanCompass.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delete")
public class DeleteController {

    @Autowired
    DeleteService deleteService;

    @PostMapping("/deleteArticle")
    public void deleteArticle(@RequestBody DeleteRequest request) {
        deleteService.deleteArticle(request);
    }

    @PostMapping("/deleteRating")
    public void deleteRating(@RequestBody DeleteRequest request) {
        deleteService.deleteRating(request);
    }
}
