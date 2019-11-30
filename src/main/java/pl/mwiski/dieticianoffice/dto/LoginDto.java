package pl.mwiski.dieticianoffice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.mwiski.dieticianoffice.entity.enums.RoleType;

import javax.validation.constraints.NotEmpty;

@Data
@RequiredArgsConstructor
public class LoginDto {

    private final long id;
    @NotEmpty
    private final String login;
    @NotEmpty
    private final String password;
    private final RoleType role;
}
