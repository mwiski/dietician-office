package pl.mwiski.dieticianoffice.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.mwiski.dieticianoffice.dto.LoginDto;
import pl.mwiski.dieticianoffice.entity.Login;
import pl.mwiski.dieticianoffice.entity.enums.RoleType;
import pl.mwiski.dieticianoffice.repository.LoginRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginMapperTest {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "pass";
    private Login login;
    private LoginDto loginDto;

    @InjectMocks
    private LoginMapper loginMapper;

    @Mock
    private LoginRepository loginRepository;

    @Before
    public void before() {
        login = new Login(1L, LOGIN, PASSWORD, RoleType.ADMIN);
        loginDto = new LoginDto(1L, LOGIN, PASSWORD, RoleType.ADMIN);
    }

    @Test
    public void shouldMapToDieticianDto() {
        //Given & When
        when(loginRepository.findByLogin(LOGIN)).thenReturn(login);
        LoginDto loginDto = loginMapper.toLoginDto(LOGIN);

        //Then
        assertThat(loginDto).isEqualToComparingFieldByField(login);
    }
}
