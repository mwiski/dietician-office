package pl.mwiski.dieticianoffice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwiski.dieticianoffice.dto.UserDto;
import pl.mwiski.dieticianoffice.service.UserService;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(UserController.BASE_API)
@Slf4j
public class UserController {

    static final String BASE_API = "v1/users";

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        log.info("Getting list of all users");
        return userService.getUsers();
    }

    @GetMapping("{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        log.info("Getting user by ID = {}", userId);
        return userService.getUser(userId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public UserDto addUser(@RequestBody UserDto userDto) {
        log.info("Creating new user {}", userDto.getLogin());
        return userService.addUser(userDto);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto userDto) {
        log.info("Updating user information {}", userDto.getLogin());
        return userService.updateUser(userDto);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("Deleting user with id {}", userId);
        userService.deleteUser(userId);
    }
}
