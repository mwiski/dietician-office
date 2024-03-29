package pl.mwiski.dieticianoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwiski.dieticianoffice.dto.UserDto;
import pl.mwiski.dieticianoffice.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(UserController.BASE_API)
public class UserController {

    static final String BASE_API = "v1/users";

    @Autowired
    private UserService userService;

    @GetMapping(value = "${api.key}", produces = APPLICATION_JSON_VALUE)
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("{userId}/${api.key}")
    public UserDto get(@PathVariable final long userId) {
        return userService.get(userId);
    }

    @GetMapping("name/{username}/${api.key}")
    public Optional<UserDto> getUserByLogin(@PathVariable final String username) {
        return userService.getUserByLogin(username);
    }

    @PostMapping(value = "${api.key}", consumes = APPLICATION_JSON_VALUE)
    public UserDto add(@RequestBody @Valid final UserDto userDto) {
        return userService.add(userDto);
    }

    @PostMapping(value = "admin/${api.key}", consumes = APPLICATION_JSON_VALUE)
    public void addAdmin(@RequestBody @Valid final UserDto userDto) {
        userService.addAdmin(userDto);
    }

    @PutMapping("${api.key}")
    public UserDto update(@RequestBody @Valid final UserDto userDto) {
        return userService.update(userDto);
    }

    @DeleteMapping("{userId}/${api.key}")
    public void delete(@PathVariable final long userId) {
        userService.delete(userId);
    }
}
