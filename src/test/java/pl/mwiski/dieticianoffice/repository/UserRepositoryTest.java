package pl.mwiski.dieticianoffice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User user;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();
    }

    @Test
    public void saveUser() {
        //Given & When
        userRepository.save(user);

        //Then
        assertThat(user.getId()).isGreaterThan(0);
        assertThat(user.getQuestions()).isEmpty();
        assertThat(user.getAddress().getCity()).isEqualTo("city");
    }

    @After
    public void after() {
        userRepository.deleteById(user.getId());
    }
}
