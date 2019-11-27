package pl.mwiski.dieticianoffice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mwiski.dieticianoffice.dto.QuestionDto;
import pl.mwiski.dieticianoffice.dto.SimpleUserDto;
import pl.mwiski.dieticianoffice.entity.Question;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.mapper.QuestionMapper;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.QuestionRepository;
import pl.mwiski.dieticianoffice.repository.UserRepository;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceTest {

    private static final String QUESTION = "Is this test?";

    @InjectMocks
    private QuestionService questionService;
    @Mock
    private QuestionMapper questionMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private UserRepository userRepository;

    private Question question;
    private QuestionDto questionDto;
    private User user;
    private SimpleUserDto simpleUserDto;

    @Before
    public void setup() {
        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();

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
    public void shouldGetAllQuestions() {
        //Given & When
        when(questionRepository.findAll()).thenReturn(Arrays.asList(question));
        when(questionMapper.toQuestionDtoList(Arrays.asList(question))).thenReturn(Arrays.asList(questionDto));

        List<QuestionDto> expected = questionService.getAll();

        //Then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(questionDto);
    }

    @Test
    public void shouldGetUserQuestions() {
        //Given & When
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(questionRepository.findAllByUser(user)).thenReturn(Arrays.asList(question));
        when(questionMapper.toQuestionDtoList(Arrays.asList(question))).thenReturn(Arrays.asList(questionDto));

        List<QuestionDto> expected = questionService.getUserQuestions(user.getId());

        //Then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(questionDto);
    }

    @Test
    public void shouldAddQuestion() {
        //Given & When
        when(questionMapper.toQuestion(questionDto)).thenReturn(question);
        when(questionRepository.save(question)).thenReturn(question);
        when(questionMapper.toQuestionDto(question)).thenReturn(questionDto);

        QuestionDto expected = questionService.add(questionDto);

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(questionDto);
    }

    @Test
    public void shouldEditQuestion() {
        //Given & When
        when(questionRepository.findById(questionDto.getId())).thenReturn(Optional.ofNullable(question));
        when(questionRepository.save(question)).thenReturn(question);
        when(questionMapper.toQuestionDto(question)).thenReturn(questionDto);

        QuestionDto expected = questionService.edit(questionDto.getId(), "edited");

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(questionDto);
        assertThat(question.getQuestion()).isEqualTo("edited");
    }

    @Test
    public void shouldDeletequestion() {
        //Given & When
        when(questionRepository.findById(questionDto.getId())).thenReturn(Optional.ofNullable(question));
        questionService.delete(questionDto.getId());

        //Then
        assertThat(user.getQuestions().size()).isEqualTo(0);
    }
}
