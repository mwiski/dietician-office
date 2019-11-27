package pl.mwiski.dieticianoffice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.entity.Answer;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.Question;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AnswerRepositoryTest {

    private static final String ANSWER = "answer";
    private static final String QUESTION = "Is this test?";

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private DieticianRepository dieticianRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private Answer answer;
    private Question question;
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
        userRepository.save(user);
        question = new Question(1L, QUESTION, user);
        answer = new Answer(1L, ANSWER, question, dietician);
        dietician.getAnswers().add(answer);
        dieticianRepository.save(dietician);
        questionRepository.save(question);
    }

    @Test
    public void shouldSaveAnswer() {
        //Given & When & Then
        assertThat(answer.getId()).isGreaterThan(0);
        assertThat(answer.getQuestion()).isEqualTo(question);
        assertThat(answer.getDietician()).isEqualTo(dietician);
        assertThat(dietician.getAnswers().get(0)).isEqualTo(answer);
    }

    @After
    public void after() {
        questionRepository.deleteById(question.getId());
        dieticianRepository.deleteById(dietician.getId());
    }
}
