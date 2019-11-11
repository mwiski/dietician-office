package pl.mwiski.dieticianoffice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.entity.*;
import pl.mwiski.dieticianoffice.repository.factory.AddressFactory;
import pl.mwiski.dieticianoffice.repository.factory.DieticianFactory;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VisitRepositoryTest {

    private static final LocalDateTime DATE_AND_TIME = LocalDateTime.of(2019, 11, 12, 10, 0, 0 );
    private static final boolean AVAILABLE = true;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TermRepository termRepository;
    @Autowired
    private DieticianRepository dieticianRepository;
    private Visit visit;
    private Address address;
    private User user;
    private Term term;
    private Dietician dietician;

    @Before
    public void setup() {
        UserFactory userFactory = new UserFactory();
        AddressFactory addressFactory = new AddressFactory();
        address = addressFactory.newInstance();
        user = userFactory.setAddress(address).newInstance();
        address.getUsers().add(user);
        addressRepository.save(address);

        term = new Term(DATE_AND_TIME, AVAILABLE);
        DieticianFactory dieticianFactory = new DieticianFactory();
        dietician = dieticianFactory.newInstance();

        dietician.getTerms().add(term);
        term.getDieticians().add(dietician);
        visit = new Visit(term, user, dietician, false);
        user.getVisits().add(visit);
        visit.setUser(user);
        termRepository.save(term);
        term.getVisits().add(visit);
        visit.setTerm(term);
        dieticianRepository.save(dietician);
        dietician.getVisits().add(visit);
        visit.setDietician(dietician);
        userRepository.save(user);
    }

    @Test
    public void saveVisit() {
        //Given & When & Then
        assertThat(visit.getUser()).isEqualTo(user);
        assertThat(visit.getTerm()).isEqualTo(term);
        assertThat(visit.getDietician()).isEqualTo(dietician);
        assertThat(dietician.getVisits().get(0)).isEqualTo(visit);
        assertThat(visit.getId()).isGreaterThan(0);
    }

    @After
    public void after() {
        dieticianRepository.deleteById(dietician.getId());
        termRepository.deleteById(term.getId());
        userRepository.deleteById(user.getId());
        addressRepository.deleteById(address.getId());
    }
}
