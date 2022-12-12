package fudan.pm.fudanCompass.controller;

import fudan.pm.fudanCompass.Utils.JsonResult;
import fudan.pm.fudanCompass.dto.RatingDetailsDto;
import fudan.pm.fudanCompass.dto.RatingOutputDto;
import fudan.pm.fudanCompass.dto.request.RatingDetailsRequest;
import fudan.pm.fudanCompass.dto.request.RatingRequest;
import fudan.pm.fudanCompass.dto.request.SearchRatingsRequest;
import fudan.pm.fudanCompass.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @PostMapping("/search")
    public Page<RatingOutputDto> search(@RequestBody SearchRatingsRequest request){
        if (ObjectUtils.isEmpty(request.getPageNo()) || ObjectUtils.isEmpty(request.getPageSize()))
            return null;
        return ratingService.search(request);
    }

    @PostMapping("/detail")
    public RatingDetailsDto getDetails(@RequestBody RatingDetailsRequest request){
        return ratingService.getDetails(request);
    }

    @PostMapping
    public void postRating(@RequestBody RatingRequest request) {
        ratingService.post(request);
    }

    @PutMapping("/{id}")
    public void updateRating(@PathVariable("id") Long id,
                             @RequestBody RatingRequest request){
        ratingService.update(id, request);
    }

    @GetMapping("/getPopCourseTypes")
    public JsonResult<?> getPopCourseTypes(){
        return new JsonResult<>(ratingService.getPopCourseTypes());
    }

    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable("id") Long id){
        ratingService.delete(id);
    }
}
