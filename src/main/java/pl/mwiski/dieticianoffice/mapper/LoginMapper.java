package pl.mwiski.dieticianoffice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mwiski.dieticianoffice.dto.LoginDto;
import pl.mwiski.dieticianoffice.entity.Login;
import pl.mwiski.dieticianoffice.exception.EntityNotFoundException;
import pl.mwiski.dieticianoffice.repository.LoginRepository;

@Component
public class LoginMapper {

    @Autowired
    private LoginRepository loginRepository;

    public LoginDto toLoginDto(final String username) {
        Login login = loginRepository.findByLogin(username);
        if (login == null) {
            throw new EntityNotFoundException(Login.class, "", "");
        }
        return new LoginDto(
                login.getId(),
                login.getLogin(),
                login.getPassword(),
                login.getRole()
                );
    }
}
