package pl.mwiski.dieticianoffice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.mwiski.dieticianoffice.entity.enums.RoleType;

@Data
@RequiredArgsConstructor
public class LoginDto {

    private final long id;
    private final String login;
    private final String password;
    private final RoleType role;
}
