package pl.mwiski.dieticianoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.dto.UserDto;
import pl.mwiski.dieticianoffice.entity.Address;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.exception.EntityNotFoundException;
import pl.mwiski.dieticianoffice.mapper.UserMapper;
import pl.mwiski.dieticianoffice.repository.AddressRepository;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    public List<UserDto> getUsers() {
        return userMapper.toUserDtoList(userRepository.findAll());
    }

    public UserDto getUser(final Long userId) {
        return userMapper.toUserDto(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId.toString())));
    }

    public UserDto addUser(final UserDto userDto) {
        User user = setUserAndAddress(userDto);
        return userMapper.toUserDto(userRepository.save(user));
    }

    public UserDto updateUser(final UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new EntityNotFoundException(User.class, "id", String.valueOf(userDto.getId())));
        User updatedUser = setUserAndAddress(userDto);
        updatedUser.setVisits(user.getVisits());
        updatedUser.setQuestions(user.getQuestions());
        updatedUser.setAnswers(user.getAnswers());
        updatedUser.setOpinions(user.getOpinions());
        return userMapper.toUserDto(userRepository.save(updatedUser));
    }

    private User setUserAndAddress(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        Address address = user.getAddress();
        Address address1;
        if ((address1 = addressRepository.findDistinctByPostalCodeAndCityAndStreetAndBuildingNumberAndApartmentNumber(
                address.getPostalCode(),
                address.getCity(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getApartmentNumber())) != null) {
            user.setAddress(address1);
            address1.getUsers().add(user);
            addressRepository.save(address1);
        } else {
            address.getUsers().add(user);
            addressRepository.save(address);
        }
        return user;
    }

    public void deleteUser(final Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class, "id", userId.toString()));
        Address address = user.getAddress();
        address.getUsers().remove(user);
        userRepository.deleteById(userId);
    }
}
