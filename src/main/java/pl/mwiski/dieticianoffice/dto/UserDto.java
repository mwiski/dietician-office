package pl.mwiski.dieticianoffice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.mwiski.dieticianoffice.entity.enums.RoleType;
import pl.mwiski.dieticianoffice.entity.enums.SexType;

import javax.validation.constraints.*;

@Data
@RequiredArgsConstructor
public class UserDto {

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
    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    private final int age;
    private final SexType sex;
    @NotNull
    private final AddressDto address;
    @NotEmpty
    private final String phoneNumber;
    @Email
    @NotEmpty
    private final String mail;
}
