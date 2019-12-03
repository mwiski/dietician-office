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
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DieticianRepositoryTest {

    private static final String LOGIN = "dietlogin";

    @Autowired
    private DieticianRepository dieticianRepository;
    private Dietician dietician;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        DieticianFactory dieticianFactory = new DieticianFactory(passwordEncoder);
        dietician = dieticianFactory.newInstance();
    }

    @Test
    public void saveDietician() {
        //Given & When
        dieticianRepository.save(dietician);

        //Then
        assertThat(dietician.getId()).isGreaterThan(0);
    }

    @Test
    public void findDieticianByLogin() {
        //Given & When
        dieticianRepository.save(dietician);
        Optional<Dietician> result = dieticianRepository.findByLogin_Login(LOGIN);

        //Then
        assertThat(result).isNotEmpty();
    }

    @After
    public void after() {
        dieticianRepository.deleteById(dietician.getId());
    }
}
