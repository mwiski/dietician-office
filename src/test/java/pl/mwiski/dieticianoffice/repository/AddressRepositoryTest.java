package pl.mwiski.dieticianoffice.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.entity.Address;
import pl.mwiski.dieticianoffice.repository.factory.AddressFactory;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;
    private Address address;

    @Before
    public void setup() {
        AddressFactory addressFactory = new AddressFactory();
        address = addressFactory.newInstance();
    }

    @Test
    public void saveAddress() {
        //Given & When
        addressRepository.save(address);

        //Then
        assertThat(address.getId()).isGreaterThan(0);
    }

    @After
    public void after() {
        addressRepository.deleteById(address.getId());
    }
}
