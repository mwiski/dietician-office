package pl.mwiski.dieticianoffice.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mwiski.dieticianoffice.dto.SimpleUserDto;
import pl.mwiski.dieticianoffice.dto.UserDto;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.entity.enums.RoleType;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;
    @Mock
    private AddressMapper addressMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;
    private UserDto userDto;
    private SimpleUserDto simpleUserDto;

    @Before
    public void setup() {
        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();
        userDto = new UserDto(
                user.getId(),
                user.getLogin().getLogin(),
                user.getLogin().getPassword(),
                user.getName(),
                user.getLastName(),
                user.getAge(),
                user.getSex(),
                addressMapper.toAddressDto(user.getAddress()),
                user.getPhoneNumber(),
                user.getMail()
        );
        simpleUserDto =  new SimpleUserDto(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getMail());
    }

    @Test
    public void shouldMapToUserDtoList() {
        //Given
        List<User> users = Arrays.asList(user);

        // When
        List<UserDto> userDtos = userMapper.toUserDtoList(users);

        //Then
        assertThat(userDtos).containsExactly(userDto);
    }

    @Test
    public void shouldMapToUserDto() {
        //Given & When
        UserDto userDto = userMapper.toUserDto(user);

        //Then
        assertThat(userDto).isEqualToComparingOnlyGivenFields(user);
    }

    @Test
    public void shouldMapToUser() {
        //Given & When
        User user = userMapper.toUser(userDto);

        //Then
        assertThat(user).isEqualToComparingOnlyGivenFields(userDto);
    }

    @Test
    public void shouldMapToAdmin() {
        //Given & When
        User user = userMapper.toAdmin(userDto);

        //Then
        assertThat(user).isEqualToComparingOnlyGivenFields(userDto);
        assertThat(user.getLogin().getRole()).isEqualTo(RoleType.ADMIN);
    }

    @Test
    public void shouldMapToUserFromSimpleUser() {
        //Given & When
        when(userRepository.findById(simpleUserDto.getId())).thenReturn(Optional.ofNullable(user));
        User user = userMapper.toUserFromSimpleUser(simpleUserDto);

        //Then
        assertThat(user).isEqualToComparingOnlyGivenFields(simpleUserDto);
    }

    @Test
    public void shouldMapToSimpleUserDto() {
        //Given & When
        SimpleUserDto simpleUserDto = userMapper.toSimpleUserDto(user);

        //Then
        assertThat(simpleUserDto).isEqualToComparingOnlyGivenFields(user);
    }
}
