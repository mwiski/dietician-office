package pl.mwiski.dieticianoffice.repository.factory;

import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mwiski.dieticianoffice.entity.Dietician;
import pl.mwiski.dieticianoffice.entity.Login;
import pl.mwiski.dieticianoffice.entity.enums.RoleType;

public class DieticianFactory {

    private PasswordEncoder passwordEncoder;
    private static final String PASSWORD = "dietpass";
    private static final String NAME = "name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE_NUMBER =  "123456789";
    private String login = "dietlogin";
    private String mail = "dietician@mail.com";

    public DieticianFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Dietician newInstance() {
        return Dietician.builder()
                .login(new Login(login, PASSWORD, RoleType.DIETICIAN))
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
