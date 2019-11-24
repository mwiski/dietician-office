package pl.mwiski.dieticianoffice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
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
public class QuestionRepositoryTest {

    private static final String QUESTION = "Is this test?";

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private DieticianRepository dieticianRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
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

        question = new Question(1L, QUESTION, user, dieticians);
        dietician.getQuestions().add(question);
        dieticianRepository.save(dietician);
        user.getQuestions().add(question);
        userRepository.save(user);
    }

    @Test
    public void saveQuestion() {
        //Given & When
        questionRepository.save(question);

        //Then
        assertThat(question.getId()).isGreaterThan(0);
        assertThat(question.getUser()).isEqualTo(user);
        assertThat(question.getDieticians().get(0)).isEqualTo(dietician);
        assertThat(user.getQuestions().get(0)).isEqualTo(question);
        assertThat(dietician.getQuestions().get(0)).isEqualTo(question);
    }

    @After
    public void after() {
        dieticianRepository.deleteById(dietician.getId());
        questionRepository.delete(question);
        userRepository.deleteById(user.getId());
    }
}
