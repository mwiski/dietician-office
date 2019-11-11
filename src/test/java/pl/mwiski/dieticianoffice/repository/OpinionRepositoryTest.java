package pl.mwiski.dieticianoffice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.entity.Address;
import pl.mwiski.dieticianoffice.entity.Opinion;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.repository.factory.AddressFactory;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpinionRepositoryTest {

    private static final String OPINION = "opinion";

    @Autowired
    private OpinionRepository opinionRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    private Opinion opinion;
    private Address address;
    private User user;

    @Before
    public void setup() {
        AddressFactory addressFactory = new AddressFactory();
        UserFactory userFactory = new UserFactory();
        address = addressFactory.newInstance();
        user = userFactory.setAddress(address).newInstance();
        address.getUsers().add(user);
        addressRepository.save(address);
        opinion = new Opinion(OPINION, user);
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
        addressRepository.deleteById(address.getId());
    }
}
