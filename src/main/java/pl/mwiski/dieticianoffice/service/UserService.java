package pl.mwiski.dieticianoffice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.dto.UserDto;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.exception.EntityNotFoundException;
import pl.mwiski.dieticianoffice.mapper.UserMapper;
import pl.mwiski.dieticianoffice.repository.LoginRepository;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginRepository loginRepository;

    public List<UserDto> getAll() {
        log.info("Getting list of all users");
        return userMapper.toUserDtoList(userRepository.findAll());
    }

    public UserDto get(final long userId) {
        log.info("Getting user by ID = [{}]", userId);
        return userMapper.toUserDto(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "ID", String.valueOf(userId))));
    }

    public UserDto add(final UserDto userDto) {
        log.info("Creating new user [{}]", userDto.getLogin());
        return userMapper.toUserDto(userRepository.save(userMapper.toUser(userDto)));
    }

    public UserDto update(final UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "ID", String.valueOf(userDto.getId())));

        log.info("Updating user information [{}]", userDto.getLogin());
        User updatedUser = userMapper.toUser(userDto);
        checkLogin(userDto, user, updatedUser);

        updatedUser.setVisits(user.getVisits());
        updatedUser.setQuestions(user.getQuestions());
        updatedUser.setAnswers(user.getAnswers());
        updatedUser.setOpinions(user.getOpinions());
        return userMapper.toUserDto(userRepository.save(updatedUser));
    }

    private void checkLogin(UserDto userDto, User user, User updatedUser) {
        if (!user.getLogin().getLogin().equals(userDto.getLogin())) {
            loginRepository.delete(user.getLogin());
        } else {
            updatedUser.setLogin(loginRepository.findByLogin(userDto.getLogin()));
        }
    }

    public void delete(final long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "ID", String.valueOf(userId)));

        log.info("Deleting user with ID [{}]", userId);
        user.getQuestions().forEach(question -> question.setUser(null));
        user.getAnswers().forEach(answer -> answer.setUser(null));
        user.getOpinions().forEach(opinion -> opinion.setUser(null));
        userRepository.deleteById(userId);
    }
}
