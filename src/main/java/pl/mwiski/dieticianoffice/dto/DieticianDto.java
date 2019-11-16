package pl.mwiski.dieticianoffice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DieticianDto {

    private final long id;
    private final String login;
    private final String password;
    private final String name;
    private final String lastName;
    private final String phoneNumber;
    private final String mail;
}
