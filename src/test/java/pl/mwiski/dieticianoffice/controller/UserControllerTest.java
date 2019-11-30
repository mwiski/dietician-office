package pl.mwiski.dieticianoffice.controller;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.mwiski.dieticianoffice.dto.AddressDto;
import pl.mwiski.dieticianoffice.dto.UserDto;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.mapper.AddressMapper;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import pl.mwiski.dieticianoffice.service.UserService;
import java.util.Arrays;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final String PASSWORD = "pass";
    private static final String NAME = "name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE_NUMBER =  "123456789";
    private static final String LOGIN = "userlogin";
    private static final String MAIL = "user@mail.com";
    private static final String CITY = "city";
    private static final String POSTAL_CODE = "01-001";
    private static final String STREET = "street";
    private static final String BUILDING_NO = "1";
    private static final String APARTMENT_NO = "1";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;
    private UserDto userDto;
    private String key =

    @Before
    public void setup() {
        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();
        userDto = new UserDto(
                user.getId(),
                user.getLogin().getLogin(),
                PASSWORD,
                user.getName(),
                user.getLastName(),
                user.getLogin().getRole(),
                user.getAge(),
                user.getSex(),
                new AddressDto(CITY, POSTAL_CODE, STREET, BUILDING_NO, APARTMENT_NO),
                user.getPhoneNumber(),
                user.getMail()
        );
    }

    @Test
    public void shouldReturnAllUsers() throws Exception {
        //Given
        when(userService.getAll()).thenReturn(Arrays.asList(userDto));

        //When & Then
        mockMvc.perform(get("/v1/users/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].login", is(LOGIN)))
                .andExpect(jsonPath("$[0].password", is(PASSWORD)))
                .andExpect(jsonPath("$[0].name", is(NAME)))
                .andExpect(jsonPath("$[0].lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[0].age", is(30)))
                .andExpect(jsonPath("$[0].sex", is("MALE")))
                .andExpect(jsonPath("$[0].phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$[0].mail", is(MAIL)));
    }

    @Test
    public void shouldGetUser() throws Exception {
        //Given
        when(userService.get(userDto.getId())).thenReturn(userDto);

        //When & Then
        mockMvc.perform(get("/v1/users/" + userDto.getId() + "/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.login", is(LOGIN)))
                .andExpect(jsonPath("$.password", is(PASSWORD)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.age", is(30)))
                .andExpect(jsonPath("$.sex", is("MALE")))
                .andExpect(jsonPath("$.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.mail", is(MAIL)));
    }

    @Test
    public void shouldGetUserByName() throws Exception {
        //Given
        when(userService.getUserByName(userDto.getName())).thenReturn(userDto);

        //When & Then
        mockMvc.perform(get("/v1/users/name/" + userDto.getName() + "/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.login", is(LOGIN)))
                .andExpect(jsonPath("$.password", is(PASSWORD)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.age", is(30)))
                .andExpect(jsonPath("$.sex", is("MALE")))
                .andExpect(jsonPath("$.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.mail", is(MAIL)));
    }

    @Test
    public void shouldAddUser() throws Exception {
        //Given
        when(userService.add(userDto)).thenReturn(userDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(post("/v1/users/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.login", is(LOGIN)))
                .andExpect(jsonPath("$.password", is(PASSWORD)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.age", is(30)))
                .andExpect(jsonPath("$.sex", is("MALE")))
                .andExpect(jsonPath("$.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.mail", is(MAIL)));
    }

    @Test
    public void shouldAddAdmin() throws Exception {
        //Given

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(post("/v1/users/admin/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        //Given
        when(userService.update(userDto)).thenReturn(userDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(put("/v1/users/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.login", is(LOGIN)))
                .andExpect(jsonPath("$.password", is(PASSWORD)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.age", is(30)))
                .andExpect(jsonPath("$.sex", is("MALE")))
                .andExpect(jsonPath("$.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.mail", is(MAIL)));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        //Given
        when(userService.get(userDto.getId())).thenReturn(userDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(delete("/v1/users/" + userDto.getId() + "/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
