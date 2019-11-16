package pl.mwiski.dieticianoffice.repository.factory;

import pl.mwiski.dieticianoffice.entity.Address;
import pl.mwiski.dieticianoffice.entity.Login;
import pl.mwiski.dieticianoffice.entity.User;
import pl.mwiski.dieticianoffice.entity.enums.RoleType;
import pl.mwiski.dieticianoffice.entity.enums.SexType;

public class UserFactory {

    private static final String PASSWORD = "pass";
    private static final String NAME = "name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE_NUMBER =  "123456789";
    private static final String POSTAL_CODE = "01-001";
    private static final String STREET = "street";
    private static final String BUILDING_NO = "1";
    private static final String APARTMENT_NO = "1";
    private String login = "userlogin";
    private String mail = "user@mail.com";
    private String city = "city";

    public UserFactory() {
    }

    public User newInstance() {
        return User.builder()
                .login(new Login(login, PASSWORD, RoleType.USER))
                .name(NAME)
                .lastName(LAST_NAME)
                .age((short) 30)
                .sex(SexType.MALE)
                .phoneNumber(PHONE_NUMBER)
                .mail(mail)
                .address(new Address(city, POSTAL_CODE, STREET, BUILDING_NO, APARTMENT_NO))
                .build();
    }

    public UserFactory setLogin(String login) {
        this.login = login;
        return this;
    }

    public UserFactory setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public UserFactory setCity(String city) {
        this.city = city;
        return this;
    }
}

