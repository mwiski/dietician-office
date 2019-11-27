package pl.mwiski.dieticianoffice.mapper;

import org.junit.Before;
import org.junit.Test;
import pl.mwiski.dieticianoffice.dto.AddressDto;
import pl.mwiski.dieticianoffice.entity.Address;
import static org.assertj.core.api.Assertions.assertThat;

public class AddressMapperTest {

    private AddressMapper addressMapper;
    private Address address;
    private AddressDto addressDto;

    private static final String CITY = "city";
    private static final String POSTAL_CODE = "01-001";
    private static final String STREET = "street";
    private static final String BUILDING_NO = "1";
    private static final String APARTMENT_NO = "1";

    @Before
    public void before() {
        addressMapper = new AddressMapper();
    }

    @Test
    public void shouldMapToAddressDto() {
        //Given
        address = new Address(CITY, POSTAL_CODE, STREET, BUILDING_NO, APARTMENT_NO);

        // When
        addressDto = addressMapper.toAddressDto(address);

        //Then
        assertThat(addressDto).isEqualToComparingFieldByField(address);
    }

    @Test
    public void shouldMapToAddress() {
        //Given
        addressDto = new AddressDto(CITY, POSTAL_CODE, STREET, BUILDING_NO, APARTMENT_NO);

        // When
        address = addressMapper.toAddress(addressDto);

        //Then
        assertThat(address).isEqualToComparingFieldByField(addressDto);
    }
}
