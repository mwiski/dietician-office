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
import pl.mwiski.dieticianoffice.dto.OpinionDto;
import pl.mwiski.dieticianoffice.dto.SimpleUserDto;
import pl.mwiski.dieticianoffice.entity.Opinion;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import pl.mwiski.dieticianoffice.service.OpinionService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OpinionController.class)
public class OpinionControllerTest {

    private static final String OPINION = "opinion";
    private static final String NAME = "name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE_NUMBER =  "123456789";
    private static final String MAIL =  "user@mail.com";

    private Opinion opinion;
    private OpinionDto opinionDto;
    private User user;
    private SimpleUserDto simpleUserDto;
    private String key =

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OpinionService opinionService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void before() {
        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();
        user.setId(1L);
        simpleUserDto = new SimpleUserDto(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getLogin().getLogin(),
                user.getLogin().getRole(),
                user.getPhoneNumber(),
                user.getMail());

        opinion = new Opinion(1L, OPINION, user);
        opinionDto = new OpinionDto(1L, OPINION, MapperUtils.dateToString(opinion.getAddedAt()), simpleUserDto);
        user.getOpinions().add(opinion);
    }

    @Test
    public void shouldReturnAllOpinions() throws Exception {
        //Given
        when(opinionService.getAll()).thenReturn(Arrays.asList(opinionDto));

        //When & Then
        mockMvc.perform(get("/v1/opinions/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].opinion", is(OPINION)))
                .andExpect(jsonPath("$[0].addedAt", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$[0].user.id", is(1)))
                .andExpect(jsonPath("$[0].user.name", is(NAME)))
                .andExpect(jsonPath("$[0].user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[0].user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$[0].user.mail", is(MAIL)));
    }

    @Test
    public void shouldGetUserOpinions() throws Exception {
        //Given
        when(opinionService.getUserOpinions(simpleUserDto.getId())).thenReturn(Arrays.asList(opinionDto));

        //When & Then
        mockMvc.perform(get("/v1/opinions/users/" + simpleUserDto.getId() + "/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].opinion", is(OPINION)))
                .andExpect(jsonPath("$[0].addedAt", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$[0].user.id", is(1)))
                .andExpect(jsonPath("$[0].user.name", is(NAME)))
                .andExpect(jsonPath("$[0].user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[0].user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$[0].user.mail", is(MAIL)));
    }

    @Test
    public void shouldAddOpinion() throws Exception {
        //Given
        when(opinionService.add(opinionDto)).thenReturn(opinionDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(opinionDto);

        //When & Then
        mockMvc.perform(post("/v1/opinions/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.opinion", is(OPINION)))
                .andExpect(jsonPath("$.addedAt", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$.user.id", is(1)))
                .andExpect(jsonPath("$.user.name", is(NAME)))
                .andExpect(jsonPath("$.user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.user.mail", is(MAIL)));
    }

    @Test
    public void shouldEditOpinion() throws Exception {
        //Given
        when(opinionService.edit(opinionDto.getId(), "content")).thenReturn(opinionDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(opinionDto);

        //When & Then
        mockMvc.perform(put("/v1/opinions/" + opinionDto.getId() + "/" + key)
                .param("content", "content")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.opinion", is(OPINION)))
                .andExpect(jsonPath("$.addedAt", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$.user.id", is(1)))
                .andExpect(jsonPath("$.user.name", is(NAME)))
                .andExpect(jsonPath("$.user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.user.mail", is(MAIL)));
    }

    @Test
    public void shouldDeleteOpinion() throws Exception {
        //Given
        Gson gson = new Gson();
        String jsonContent = gson.toJson(opinionDto);

        //When & Then
        mockMvc.perform(delete("/v1/opinions/" + opinionDto.getId() + "/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
