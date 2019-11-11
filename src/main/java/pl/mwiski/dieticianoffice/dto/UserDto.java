package pl.mwiski.dieticianoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private long id;
    private String login;
    private String password;
    private String name;
    private String lastName;
    private AddressDto address;
    private String phoneNumber;
    private String mail;
}
