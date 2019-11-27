package pl.mwiski.dieticianoffice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.mwiski.dieticianoffice.dto.LoginDto;
import pl.mwiski.dieticianoffice.entity.enums.RoleType;
import pl.mwiski.dieticianoffice.mapper.LoginMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "pass";
    private LoginDto loginDto;

    @InjectMocks
    private LoginService loginService;
    @Mock
    private LoginMapper loginMapper;

    @Before
    public void before() {
        loginDto = new LoginDto(1L, LOGIN, PASSWORD, RoleType.ADMIN);
    }

    @Test
    public void shouldGetLogin() {
        //Given & When
        when(loginMapper.toLoginDto(LOGIN)).thenReturn(loginDto);

        LoginDto expected = loginService.getLogin(LOGIN);

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(loginDto);
    }
}
