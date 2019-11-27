package pl.mwiski.dieticianoffice.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mwiski.dieticianoffice.dto.QuestionDto;
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
public class QuestionMapperTest {

    private static final String QUESTION = "Is this test?";

    @InjectMocks
    private QuestionMapper questionMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    private Question question;
    private QuestionDto questionDto;
    private Dietician dietician;
    private User user;

    @Before
    public void setup() {
        DieticianFactory dieticianFactory = new DieticianFactory(passwordEncoder);
        dietician = dieticianFactory.newInstance();
        List<Dietician> dieticians = new ArrayList<>();
        dieticians.add(dietician);

        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();

        question = new Question(1L, QUESTION, user);
        questionDto = new QuestionDto(
                1L,
                QUESTION,
                MapperUtils.dateToString(question.getAddedAt()),
                userMapper.toSimpleUserDto(user));
    }

    @Test
    public void shouldMapToAnswerDtoList() {
        //Given
        List<Question> questions = Arrays.asList(question);

        // When
        List<QuestionDto> questionDtos = questionMapper.toQuestionDtoList(questions);

        //Then
        assertThat(questionDtos).containsExactly(questionDto);
    }

    @Test
    public void shouldMapToAnswerDto() {
        //Given & When
        QuestionDto questionDto = questionMapper.toQuestionDto(question);

        //Then
        assertThat(questionDto).isEqualToComparingOnlyGivenFields(question);
        assertThat(questionDto.getAddedAt()).isEqualTo(MapperUtils.dateToString(question.getAddedAt()));
    }

    @Test
    public void shouldMapToAnswer() {
        //Given & When
        Question question = questionMapper.toQuestion(questionDto);

        //Then
        assertThat(question).isEqualToComparingOnlyGivenFields(questionDto);
        assertThat(question.getAddedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).isEqualTo(questionDto.getAddedAt());
    }
}
