package pl.mwiski.dieticianoffice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.Term;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TermRepositoryTest {

    private static final LocalDateTime DATE_AND_TIME = LocalDateTime.of(2019, 11, 12, 10, 0, 0 );
    private static final boolean AVAILABLE = true;

    @Autowired
    private TermRepository termRepository;
    @Autowired
    private DieticianRepository dieticianRepository;
    private Term term;
    private Dietician dietician;

    @Before
    public void setup() {
        term = new Term(DATE_AND_TIME, AVAILABLE);
        DieticianFactory dieticianFactory = new DieticianFactory();
        dietician = dieticianFactory.newInstance();
        dieticianRepository.save(dietician);
    }

    @Test
    public void saveTerm() {
        //Given & When
        termRepository.save(term);

        //Then
        assertThat(term.getId()).isGreaterThan(0);
    }

    @Test
    public void addTermAndDietician() {
        //Given & When
        termRepository.save(term);
        dietician.getTerms().add(term);
        term.getDieticians().add(dietician);

        //Then
        assertThat(dietician.getTerms().get(0)).isEqualTo(term);
        assertThat(term.getDieticians().get(0)).isEqualTo(dietician);
        assertThat(term.getId()).isGreaterThan(0);
        assertThat(dietician.getId()).isGreaterThan(0);
    }

    @After
    public void after() {
        dieticianRepository.deleteById(dietician.getId());
        termRepository.deleteById(term.getId());
    }
}
