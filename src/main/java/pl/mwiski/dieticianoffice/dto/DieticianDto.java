package pl.mwiski.dieticianoffice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.mwiski.dieticianoffice.entity.enums.RoleType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class DieticianDto {

    private final long id;
    @NotEmpty
    private final String login;
    @NotEmpty
    private final String password;
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String lastName;
    @NotNull
    private final RoleType roleType;
    @NotEmpty
    private final String phoneNumber;
    @Email
    @NotEmpty
    private final String mail;
}
