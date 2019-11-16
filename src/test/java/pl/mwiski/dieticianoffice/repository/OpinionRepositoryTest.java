package pl.mwiski.dieticianoffice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.entity.Opinion;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OpinionRepositoryTest {

    private static final String OPINION = "opinion";

    @Autowired
    private OpinionRepository opinionRepository;
    @Autowired
    private UserRepository userRepository;
    private Opinion opinion;
    private User user;

    @Before
    public void setup() {
        UserFactory userFactory = new UserFactory();
        user = userFactory.newInstance();
        opinion = new Opinion(1L, OPINION, user);
        user.getOpinions().add(opinion);
        opinion.setUser(user);
        userRepository.save(user);
    }

    @Test
    public void saveOpinion() {
        //Given & When
        opinionRepository.save(opinion);

        //Then
        assertThat(opinion.getId()).isGreaterThan(0);
    }

    @After
    public void after() {
        opinionRepository.deleteById(opinion.getId());
        userRepository.deleteById(user.getId());
    }
}
