package pl.mwiski.dieticianoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwiski.dieticianoffice.dto.LoginDto;
import pl.mwiski.dieticianoffice.service.LoginService;

@RestController
@RequestMapping(LoginController.BASE_API)
public class LoginController {

    static final String BASE_API = "v1/login";

    @Autowired
    private LoginService loginService;

    @GetMapping("{username}/${api.key}")
    public LoginDto get(@PathVariable final String username) {
        return loginService.getLogin(username);
    }
}
