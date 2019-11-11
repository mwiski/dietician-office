package pl.mwiski.dieticianoffice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.entity.Address;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.repository.factory.AddressFactory;
import pl.mwiski.dieticianoffice.repository.factory.UserFactory;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    private Address address;
    private User user;

    @Before
    public void setup() {
        UserFactory userFactory = new UserFactory();
        AddressFactory addressFactory = new AddressFactory();
        address = addressFactory.newInstance();
        user = userFactory.setAddress(address).newInstance();
        address.getUsers().add(user);
        addressRepository.save(address);
    }

    @Test
    public void saveUser() {
        //Given & When
        userRepository.save(user);

        //Then
        assertThat(user.getId()).isGreaterThan(0);
        assertThat(user.getQuestions()).isNotNull();
        assertThat(user.getQuestions()).isEmpty();
    }

    @After
    public void after() {
        userRepository.deleteById(user.getId());
        addressRepository.deleteById(address.getId());
    }
}
