package pl.mwiski.dieticianoffice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.entity.*;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class VisitRepositoryTest {

    private static final LocalDateTime DATE_AND_TIME = LocalDateTime.of(2019, 11, 12, 10, 0, 0 );
    private static final boolean AVAILABLE = true;

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DieticianRepository dieticianRepository;
    private Visit visit;
    private User user;
    private Dietician dietician;

    @Before
    public void setup() {
        UserFactory userFactory = new UserFactory(passwordEncoder);
        user = userFactory.newInstance();

        DieticianFactory dieticianFactory = new DieticianFactory(passwordEncoder);
        dietician = dieticianFactory.newInstance();

        visit = new Visit(DATE_AND_TIME, user, dietician, AVAILABLE);
        dieticianRepository.save(dietician);
        user.getVisits().add(visit);
        dietician.getVisits().add(visit);
        userRepository.save(user);
    }

    @Test
    public void saveVisit() {
        //Given & When & Then
        assertThat(visit.getUser()).isEqualTo(user);
        assertThat(visit.getDietician()).isEqualTo(dietician);
        assertThat(dietician.getVisits().get(0)).isEqualTo(visit);
        assertThat(user.getVisits().get(0)).isEqualTo(visit);
        assertThat(visit.getId()).isGreaterThan(0);
    }

    @Test
    public void shouldFindAllByAvailableAndDate() {
        //Given & When
        List<Visit> visits = visitRepository.findAllByAvailableAndDate(LocalDate.of(2019,11, 12));

        //Then
        assertThat(visits.size()).isEqualTo(1);
        assertThat(visits).containsExactly(visit);
    }

    @Test
    public void shouldFindAllByDieticianAndDate() {
        //Given & When
        List<Visit> visits = visitRepository.findAllByDieticianAndDate(dietician, LocalDate.of(2019,11, 12));

        //Then
        assertThat(visits.size()).isEqualTo(1);
        assertThat(visits).containsExactly(visit);
    }

    @After
    public void after() {
        dieticianRepository.deleteById(dietician.getId());
        userRepository.deleteById(user.getId());
    }
}
