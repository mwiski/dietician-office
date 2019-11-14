package pl.mwiski.dieticianoffice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.entity.Answer;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.Question;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AnswerRepositoryTest {

    private static final String ANSWER = "answer";
    private static final String QUESTION = "Is this test?";

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private DieticianRepository dieticianRepository;
    private Answer answer;
    private Question question;
    private Dietician dietician;

    @Before
    public void setup() {
        DieticianFactory dieticianFactory = new DieticianFactory();
        dietician = dieticianFactory.newInstance();
        question = new Question(QUESTION);
        answer = new Answer(ANSWER, question);
        dietician.getAnswers().add(answer);
        answer.setDietician(dietician);
        dieticianRepository.save(dietician);
        questionRepository.save(question);
    }

    @Test
    public void saveAnswer() {
        //Given & When
        answerRepository.save(answer);

        //Then
        assertThat(answer.getId()).isGreaterThan(0);
        assertThat(answer.getQuestion()).isEqualTo(question);
        assertThat(answer.getDietician()).isEqualTo(dietician);
        assertThat(dietician.getAnswers().get(0)).isEqualTo(answer);
    }

    @After
    public void after() {
        answerRepository.deleteById(answer.getId());
        questionRepository.deleteById(question.getId());
        dieticianRepository.deleteById(dietician.getId());
    }
}
