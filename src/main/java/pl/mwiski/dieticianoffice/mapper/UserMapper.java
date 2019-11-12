package pl.mwiski.dieticianoffice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mwiski.dieticianoffice.dto.UserDto;
import pl.mwiski.dieticianoffice.entity.User;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    private AddressMapper addressMapper;

    private static <E, D> List<D> getConvertedList(Collection<E> entityList, Function<E, D> convertFunction) {
        if (entityList == null) return null;
        return entityList.stream()
                .map(convertFunction)
                .collect(Collectors.toList());
    }

    public List<UserDto> toUserDtoList(final List<User> users) {
        return getConvertedList(users, this::toUserDto);
    }

    public UserDto toUserDto(final User user) {
        if (user == null) return null;
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getName(),
                user.getLastName(),
                addressMapper.toAddressDto(user.getAddress()),
                user.getPhoneNumber(),
                user.getMail()
        );
    }

    public User toUser(final UserDto userDto) {
        if (userDto == null) return null;
        return User.builder()
                .id(userDto.getId())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .address(addressMapper.toAddress(userDto.getAddress()))
                .phoneNumber(userDto.getPhoneNumber())
                .mail(userDto.getMail())
                .build();
    }

    public UserDto toSimpleUserDto(final User user) {
        if (user == null) return null;
        return new UserDto(user.getName(), user.getLastName(), user.getPhoneNumber(), user.getMail());
    }
}
