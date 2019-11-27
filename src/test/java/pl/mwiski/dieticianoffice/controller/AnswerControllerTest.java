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
import pl.mwiski.dieticianoffice.dto.AnswerDto;
import pl.mwiski.dieticianoffice.entity.Answer;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.Question;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.mapper.DieticianMapper;
import pl.mwiski.dieticianoffice.mapper.QuestionMapper;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import pl.mwiski.dieticianoffice.service.AnswerService;
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
@WebMvcTest(AnswerController.class)
public class AnswerControllerTest {

    private static final String ANSWER = "answer";
    private static final String QUESTION = "Is this test?";
    private String key =

    private Answer answer;
    private AnswerDto answerDto;
    private Question question;
    private Dietician dietician;
    private User user;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnswerService answerService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private QuestionMapper questionMapper;
    @Mock
    private DieticianMapper dieticianMapper;

    @Before
    public void before() {
        DieticianFactory dieticianFactory = new DieticianFactory(passwordEncoder);
        dietician = dieticianFactory.newInstance();
        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();
        question = new Question(1L, QUESTION, user);
        answer = new Answer(1L, ANSWER, question, dietician);
        answerDto = new AnswerDto(1L, ANSWER, questionMapper.toQuestionDto(question), MapperUtils.dateToString(answer.getAddedAt()), dieticianMapper.toSimpleDieticianDto(dietician));
    }

    @Test
    public void shouldReturnAllAnswers() throws Exception {
        //Given
        when(answerService.getAll()).thenReturn(Arrays.asList(answerDto));

        //When & Then
        mockMvc.perform(get("/v1/answers/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].answer", is(ANSWER)))
                .andExpect(jsonPath("$[0].addedAt", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))));
    }

    @Test
    public void shouldGetDieticianAnswers() throws Exception {
        //Given
        when(answerService.getDieticianAnswers(dietician.getId())).thenReturn(Arrays.asList(answerDto));

        //When & Then
        mockMvc.perform(get("/v1/answers/dieticians/" + dietician.getId() + "/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].answer", is(ANSWER)))
                .andExpect(jsonPath("$[0].addedAt", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))));
    }

    @Test
    public void shouldAddAnswer() throws Exception {
        //Given
        when(answerService.add(answerDto)).thenReturn(answerDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(answerDto);

        //When & Then
        mockMvc.perform(post("/v1/answers/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.answer", is(ANSWER)))
                .andExpect(jsonPath("$.addedAt", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))));
    }

    @Test
    public void shouldEditAnswer() throws Exception {
        //Given
        when(answerService.edit(answerDto.getId(), "content")).thenReturn(answerDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(answerDto);

        //When & Then
        mockMvc.perform(put("/v1/answers/" + answerDto.getId() + "/" + key)
                .param("content", "content")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.answer", is(ANSWER)))
                .andExpect(jsonPath("$.addedAt", is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))));
    }

    @Test
    public void shouldDeleteAnswer() throws Exception {
        //Given
        Gson gson = new Gson();
        String jsonContent = gson.toJson(answerDto);

        //When & Then
        mockMvc.perform(delete("/v1/answers/" + answerDto.getId() + "/" + key)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
