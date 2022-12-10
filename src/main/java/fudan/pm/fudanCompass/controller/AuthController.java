package fudan.pm.fudanCompass.controller;

import fudan.pm.fudanCompass.dto.UserDto;
import fudan.pm.fudanCompass.dto.request.AuthRequest;
import fudan.pm.fudanCompass.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody AuthRequest request) throws Exception {
        authService.register(request);
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody AuthRequest request) throws Exception {
        return authService.login(request);
    }

}
