package pl.mwiski.dieticianoffice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mwiski.dieticianoffice.dto.AnswerDto;
import pl.mwiski.dieticianoffice.entity.Answer;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.Question;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.mapper.AnswerMapper;
import pl.mwiski.dieticianoffice.mapper.DieticianMapper;
import pl.mwiski.dieticianoffice.mapper.QuestionMapper;
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.AnswerRepository;
import pl.mwiski.dieticianoffice.repository.DieticianRepository;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnswerServiceTest {

    private static final String ANSWER = "answer";
    private static final String QUESTION = "Is this test?";

    private Answer answer;
    private AnswerDto answerDto;
    private Question question;
    private Dietician dietician;
    private User user;

    @InjectMocks
    private AnswerService answerService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private DieticianMapper dieticianMapper;
    @Mock
    private QuestionMapper questionMapper;
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private DieticianRepository dieticianRepository;
    @Mock
    private AnswerMapper answerMapper;

    @Before
    public void before() {
        DieticianFactory dieticianFactory = new DieticianFactory(passwordEncoder);
        dietician = dieticianFactory.newInstance();
        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();
        question = new Question(1L, QUESTION, user);
        answer = new Answer(1L, ANSWER, question, dietician);
        answerDto = new AnswerDto(1L, ANSWER, questionMapper.toQuestionDto(question), MapperUtils.dateToString(answer.getAddedAt()), dieticianMapper.toSimpleDieticianDto(dietician));
        dietician.getAnswers().add(answer);
    }

    @Test
    public void shouldGetAllAnswers() {
        //Given & When
        when(answerRepository.findAll()).thenReturn(Arrays.asList(answer));
        when(answerMapper.toAnswerDtoList(Arrays.asList(answer))).thenReturn(Arrays.asList(answerDto));

        List<AnswerDto> expected = answerService.getAll();

        //Then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(answerDto);
    }

    @Test
    public void shouldGetDieticianAnswers() {
        //Given & When
        when(dieticianRepository.findById(dietician.getId())).thenReturn(Optional.ofNullable(dietician));
        when(answerRepository.findAllByDietician(dietician)).thenReturn(Arrays.asList(answer));
        when(answerMapper.toAnswerDtoList(Arrays.asList(answer))).thenReturn(Arrays.asList(answerDto));

        List<AnswerDto> expected = answerService.getDieticianAnswers(user.getId());

        //Then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0)).isEqualToComparingOnlyGivenFields(answerDto);
    }

    @Test
    public void shouldAddAnswer() {
        //Given & When
        when(answerMapper.toAnswer(answerDto)).thenReturn(answer);
        when(answerRepository.save(answer)).thenReturn(answer);
        when(answerMapper.toAnswerDto(answer)).thenReturn(answerDto);

        AnswerDto expected = answerService.add(answerDto);

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(answerDto);
    }

    @Test
    public void shouldEditAnswer() {
        //Given & When
        when(answerRepository.findById(answerDto.getId())).thenReturn(Optional.ofNullable(answer));
        when(answerRepository.save(answer)).thenReturn(answer);
        when(answerMapper.toAnswerDto(answer)).thenReturn(answerDto);

        AnswerDto expected = answerService.edit(answerDto.getId(), "edited");

        //Then
        assertThat(expected).isEqualToComparingOnlyGivenFields(answerDto);
        assertThat(answer.getAnswer()).isEqualTo("edited");
    }

    @Test
    public void shouldDeleteAnswer() {
        //Given & When
        when(answerRepository.findById(answerDto.getId())).thenReturn(Optional.ofNullable(answer));
        answerService.delete(answerDto.getId());

        //Then
        assertThat(dietician.getAnswers().size()).isEqualTo(0);
    }
}
