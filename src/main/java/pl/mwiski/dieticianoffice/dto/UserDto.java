package pl.mwiski.dieticianoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import pl.mwiski.dieticianoffice.entity.enums.SexType;

import javax.validation.constraints.Email;

@Data
@RequiredArgsConstructor
public class UserDto {

    private final long id;
    private final String login;
    private final String password;
    private final String name;
    private final String lastName;
    private final short age;
    private final SexType sex;
    private final AddressDto address;
    private final String phoneNumber;
    @Email
    private final String mail;
}
