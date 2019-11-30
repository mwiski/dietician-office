package pl.mwiski.dieticianoffice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.mwiski.dieticianoffice.dto.SimpleUserDto;
import pl.mwiski.dieticianoffice.dto.UserDto;
import pl.mwiski.dieticianoffice.entity.Login;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.entity.enums.RoleType;
import pl.mwiski.dieticianoffice.exception.EntityNotFoundException;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import java.util.List;

@Component
public class UserMapper {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDto> toUserDtoList(final List<User> users) {
        return MapperUtils.getConvertedList(users, this::toUserDto);
    }

    public UserDto toUserDto(final User user) {
        if (user == null) return null;
        return new UserDto(
                user.getId(),
                user.getLogin().getLogin(),
                user.getLogin().getPassword(),
                user.getName(),
                user.getLastName(),
                user.getLogin().getRole(),
                user.getAge(),
                user.getSex(),
                addressMapper.toAddressDto(user.getAddress()),
                user.getPhoneNumber(),
                user.getMail()
        );
    }

    public User toUser(final UserDto userDto) {
        if (userDto == null) return null;
        return User.builder()
                .id(userDto.getId())
                .login(new Login(userDto.getLogin(), passwordEncoder.encode(userDto.getPassword()), RoleType.USER))
                .age(userDto.getAge())
                .sex(userDto.getSex())
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .address(addressMapper.toAddress(userDto.getAddress()))
                .phoneNumber(userDto.getPhoneNumber())
                .mail(userDto.getMail())
                .build();
    }

    public User toAdmin(final UserDto userDto) {
        if (userDto == null) return null;
        return User.builder()
                .id(userDto.getId())
                .login(new Login(userDto.getLogin(), passwordEncoder.encode(userDto.getPassword()), RoleType.ADMIN))
                .age(userDto.getAge())
                .sex(userDto.getSex())
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .address(addressMapper.toAddress(userDto.getAddress()))
                .phoneNumber(userDto.getPhoneNumber())
                .mail(userDto.getMail())
                .build();
    }

    public User toUserFromSimpleUser(final SimpleUserDto simpleUserDto) {
        if (simpleUserDto == null) return null;
        return userRepository.findById(simpleUserDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "ID", String.valueOf(simpleUserDto.getId())));
    }

    public SimpleUserDto toSimpleUserDto(final User user) {
        if (user == null) return null;
        return new SimpleUserDto(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getLogin().getLogin(),
                user.getLogin().getRole(),
                user.getPhoneNumber(),
                user.getMail());
    }
}
