package pl.mwiski.dieticianoffice.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.mwiski.dieticianoffice.dto.LoginDto;
import pl.mwiski.dieticianoffice.entity.enums.RoleType;
import pl.mwiski.dieticianoffice.service.LoginService;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "pass";
    private LoginDto loginDto;
    private String key;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LoginService loginService;

    @Before
    public void before() {
        loginDto = new LoginDto(1L, LOGIN, PASSWORD, RoleType.ADMIN);
    }

    @Test
    public void shouldGetLogin() throws Exception {
        //Given
        when(loginService.getLogin(loginDto.getLogin())).thenReturn(loginDto);

        //When & Then
        mockMvc.perform(get("/v1/login/" + loginDto.getLogin() + "/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.login", is(LOGIN)))
                .andExpect(jsonPath("$.password", is(PASSWORD)))
                .andExpect(jsonPath("$.role", is("ADMIN")));
    }
}
