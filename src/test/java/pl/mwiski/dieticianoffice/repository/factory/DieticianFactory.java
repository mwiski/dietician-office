package pl.mwiski.dieticianoffice.repository.factory;

import pl.mwiski.dieticianoffice.entity.Dietician;

public class DieticianFactory {

    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE_NUMBER =  "123456789";
    private String login = "login";
    private String mail = "dietician@mail.com";

    public DieticianFactory() {
    }

    public Dietician newInstance() {
        return Dietician.builder()
                .login(login)
                .password(PASSWORD)
                .name(NAME)
                .lastName(LAST_NAME)
                .phoneNumber(PHONE_NUMBER)
                .mail(mail)
                .build();
    }

    public DieticianFactory setLogin(String login) {
        this.login = login;
        return this;
    }

    public DieticianFactory setMail(String mail) {
        this.mail = mail;
        return this;
    }
}
