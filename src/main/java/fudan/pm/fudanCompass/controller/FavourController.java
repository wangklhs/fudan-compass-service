package fudan.pm.fudanCompass.controller;

import fudan.pm.fudanCompass.dto.request.FavourRequest;
import fudan.pm.fudanCompass.service.FavourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favour")
public class FavourController {

    @Autowired
    FavourService favourService;

    @PostMapping
    public void favour(@RequestBody FavourRequest request) {
        favourService.favour(request);
    }

}
