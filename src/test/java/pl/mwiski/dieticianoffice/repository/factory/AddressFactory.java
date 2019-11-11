package pl.mwiski.dieticianoffice.repository.factory;

import pl.mwiski.dieticianoffice.entity.Address;

public class AddressFactory {

    private static final String POSTAL_CODE = "01-001";
    private static final String STREET = "street";
    private static final String BUILDING_NO = "1";
    private static final String APARTMENT_NO = "1";
    private String city = "city";

    public AddressFactory() {
    }

    public Address newInstance() {
        return new Address(city, POSTAL_CODE, STREET, BUILDING_NO, APARTMENT_NO);
    }

    public AddressFactory setCity(String city) {
        this.city = city;
        return this;
    }
}
