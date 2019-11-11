package pl.mwiski.dieticianoffice.repository.factory;

import pl.mwiski.dieticianoffice.entity.Address;
import pl.mwiski.dieticianoffice.entity.User;

public class UserFactory {

    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE_NUMBER =  "123456789";
    private String login = "login";
    private String mail = "user@mail.com";
    private Address address;

    public UserFactory() {
    }

    public User newInstance() {
        return User.builder()
                .login(login)
                .password(PASSWORD)
                .name(NAME)
                .lastName(LAST_NAME)
                .phoneNumber(PHONE_NUMBER)
                .mail(mail)
                .address(address)
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

    public UserFactory setAddress(Address address) {
        this.address = address;
        return this;
    }
}

