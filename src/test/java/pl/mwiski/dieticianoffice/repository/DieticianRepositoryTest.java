package pl.mwiski.dieticianoffice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DieticianRepositoryTest {

    @Autowired
    private DieticianRepository dieticianRepository;
    private Dietician dietician;

    @Before
    public void setup() {
        DieticianFactory dieticianFactory = new DieticianFactory();
        dietician = dieticianFactory.newInstance();
    }

    @Test
    public void saveDietician() {
        //Given & When
        dieticianRepository.save(dietician);

        //Then
        assertThat(dietician.getId()).isGreaterThan(0);
    }

    @After
    public void after() {
        dieticianRepository.deleteById(dietician.getId());
    }
}
