package pl.mwiski.dieticianoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.dto.LoginDto;
import pl.mwiski.dieticianoffice.mapper.LoginMapper;
import javax.transaction.Transactional;

@Service
@Transactional
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;

    public LoginDto getLogin(final String login) {
        return loginMapper.toLoginDto(login);
    }
}
