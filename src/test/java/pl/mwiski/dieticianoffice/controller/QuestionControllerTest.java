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
import pl.mwiski.dieticianoffice.dto.QuestionDto;
import pl.mwiski.dieticianoffice.dto.SimpleUserDto;
import pl.mwiski.dieticianoffice.entity.Question;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import pl.mwiski.dieticianoffice.service.QuestionService;
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
@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {

    private String key =
    private static final String QUESTION = "Is this test?";
    private static final String NAME = "name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE_NUMBER =  "123456789";
    private static final String MAIL =  "user@mail.com";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService questionService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Question question;
    private QuestionDto questionDto;
    private User user;
    private SimpleUserDto simpleUserDto;

    @Before
    public void setup() {
        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();
        user.setId(1L);

        simpleUserDto = new SimpleUserDto(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getMail());

        question = new Question(1L, QUESTION, user);
        questionDto = new QuestionDto(
                1L,
                QUESTION,
                MapperUtils.dateToString(question.getAddedAt()),
                simpleUserDto);
        user.getQuestions().add(question);
    }

    @Test
    public void shouldReturnAllQuestions() throws Exception {
        //Given
        when(questionService.getAll()).thenReturn(Arrays.asList(questionDto));

        //When & Then
        mockMvc.perform(get("/v1/questions/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].question", is(QUESTION)))
                .andExpect(jsonPath("$[0].addedAt", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$[0].user.id", is(1)))
                .andExpect(jsonPath("$[0].user.name", is(NAME)))
                .andExpect(jsonPath("$[0].user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[0].user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$[0].user.mail", is(MAIL)));
    }

    @Test
    public void shouldGetUserQuestions() throws Exception {
        //Given
        when(questionService.getUserQuestions(simpleUserDto.getId())).thenReturn(Arrays.asList(questionDto));

        //When & Then
        mockMvc.perform(get("/v1/questions/users/" + simpleUserDto.getId() + "/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].question", is(QUESTION)))
                .andExpect(jsonPath("$[0].addedAt", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$[0].user.id", is(1)))
                .andExpect(jsonPath("$[0].user.name", is(NAME)))
                .andExpect(jsonPath("$[0].user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$[0].user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$[0].user.mail", is(MAIL)));
    }

    @Test
    public void shouldAddQuestion() throws Exception {
        //Given
        when(questionService.add(questionDto)).thenReturn(questionDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(questionDto);

        //When & Then
        mockMvc.perform(post("/v1/questions/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.question", is(QUESTION)))
                .andExpect(jsonPath("$.addedAt", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$.user.id", is(1)))
                .andExpect(jsonPath("$.user.name", is(NAME)))
                .andExpect(jsonPath("$.user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.user.mail", is(MAIL)));
    }

    @Test
    public void shouldEditQuestion() throws Exception {
        //Given
        when(questionService.edit(questionDto.getId(), "content")).thenReturn(questionDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(questionDto);

        //When & Then
        mockMvc.perform(put("/v1/questions/" + questionDto.getId() + "/" + key)
                .param("content", "content")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.question", is(QUESTION)))
                .andExpect(jsonPath("$.addedAt", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))))
                .andExpect(jsonPath("$.user.id", is(1)))
                .andExpect(jsonPath("$.user.name", is(NAME)))
                .andExpect(jsonPath("$.user.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.user.phoneNumber", is(PHONE_NUMBER)))
                .andExpect(jsonPath("$.user.mail", is(MAIL)));
    }

    @Test
    public void shouldDeleteQuestion() throws Exception {
        //Given
        Gson gson = new Gson();
        String jsonContent = gson.toJson(questionDto);

        //When & Then
        mockMvc.perform(delete("/v1/questions/" + questionDto.getId() + "/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
