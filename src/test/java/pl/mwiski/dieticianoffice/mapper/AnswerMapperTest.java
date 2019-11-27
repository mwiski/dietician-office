package pl.mwiski.dieticianoffice.mapper;

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
import pl.mwiski.dieticianoffice.mapper.utils.MapperUtils;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AnswerMapperTest {

    private static final String ANSWER = "answer";
    private static final String QUESTION = "Is this test?";

    private Answer answer;
    private AnswerDto answerDto;
    private Question question;
    private Dietician dietician;
    private User user;

    @InjectMocks
    private AnswerMapper answerMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private DieticianMapper dieticianMapper;
    @Mock
    private QuestionMapper questionMapper;

    @Before
    public void before() {
        DieticianFactory dieticianFactory = new DieticianFactory(passwordEncoder);
        dietician = dieticianFactory.newInstance();
        List<Dietician> dieticians = new ArrayList<>();
        dieticians.add(dietician);
        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();
        question = new Question(1L, QUESTION, user);
        answer = new Answer(1L, ANSWER, question, dietician);
        answerDto = new AnswerDto(1L, ANSWER, questionMapper.toQuestionDto(question), MapperUtils.dateToString(answer.getAddedAt()), dieticianMapper.toSimpleDieticianDto(dietician));
    }

    @Test
    public void shouldMapToAnswerDtoList() {
        //Given
        List<Answer> answers = Arrays.asList(answer);

        // When
        List<AnswerDto> answerDtos = answerMapper.toAnswerDtoList(answers);

        //Then
        assertThat(answerDtos).containsExactly(answerDto);
    }

    @Test
    public void shouldMapToAnswerDto() {
        //Given & When
        AnswerDto answerDto = answerMapper.toAnswerDto(answer);

        //Then
        assertThat(answerDto).isEqualToComparingOnlyGivenFields(answer);
        assertThat(answerDto.getAddedAt()).isEqualTo(MapperUtils.dateToString(answer.getAddedAt()));
    }

    @Test
    public void shouldMapToAnswer() {
        //Given & When
        Answer answer = answerMapper.toAnswer(answerDto);

        //Then
        assertThat(answer).isEqualToComparingOnlyGivenFields(answerDto);
        assertThat(answer.getAddedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).isEqualTo(answerDto.getAddedAt());
    }
}
