package pl.mwiski.dieticianoffice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mwiski.dieticianoffice.dto.UserDto;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.mapper.AddressMapper;
import pl.mwiski.dieticianoffice.mapper.UserMapper;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private AddressMapper addressMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;
    private UserDto userDto;

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
                user.getLogin().getRole(),
                user.getAge(),
                user.getSex(),
                addressMapper.toAddressDto(user.getAddress()),
                user.getPhoneNumber(),
                user.getMail()
        );
    }

    @Test
    public void shouldGetAllUsers() {
        //Given & When
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(userMapper.toUserDtoList(Arrays.asList(user))).thenReturn(Arrays.asList(userDto));

        List<UserDto> expected = userService.getAll();

        //Then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(userDto);
    }

    @Test
    public void shouldGetUser() {
        //Given & When
        when(userRepository.findById(userDto.getId())).thenReturn(Optional.ofNullable(user));
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        UserDto expected = userService.get(userDto.getId());

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(userDto);
    }

    @Test
    public void shouldGetUserByName() {
        //Given & When
        when(userRepository.findByLogin_Login(userDto.getName())).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        UserDto expected = userService.getUserByName(userDto.getName());

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(userDto);
    }

    @Test
    public void shouldAddUser() {
        //Given & When
        when(userMapper.toUser(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        UserDto expected = userService.add(userDto);

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(userDto);
    }
}
