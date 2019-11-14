package pl.mwiski.dieticianoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwiski.dieticianoffice.dto.UserDto;
import pl.mwiski.dieticianoffice.service.UserService;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(UserController.BASE_API)
public class UserController {

    static final String BASE_API = "v1/users";

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("{userId}")
    public UserDto get(@PathVariable final long userId) {
        return userService.get(userId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public UserDto add(@RequestBody final UserDto userDto) {
        return userService.add(userDto);
    }

    @PutMapping
    public UserDto update(@RequestBody final UserDto userDto) {
        return userService.update(userDto);
    }

    @DeleteMapping("{userId}")
    public void delete(@PathVariable final long userId) {
        userService.delete(userId);
    }
}
