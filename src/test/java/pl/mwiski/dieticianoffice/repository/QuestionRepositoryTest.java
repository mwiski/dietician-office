package pl.mwiski.dieticianoffice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.entity.Question;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class QuestionRepositoryTest {

    private static final String QUESTION = "Is this test?";

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    private Question question;
    private User user;

    @Before
    public void setup() {
        UserFactory userFactory = new UserFactory();
        user = userFactory.newInstance();
        question = new Question(QUESTION);
        question.setUser(user);
        user.getQuestions().add(question);
        question.setUser(user);
        userRepository.save(user);
    }

    @Test
    public void saveQuestion() {
        //Given & When
        questionRepository.save(question);

        //Then
        assertThat(question.getId()).isGreaterThan(0);
        assertThat(question.getUser()).isEqualTo(user);
        assertThat(user.getQuestions().get(0)).isEqualTo(question);
    }

    @After
    public void after() {
        questionRepository.deleteById(question.getId());
        userRepository.deleteById(user.getId());
    }
}
