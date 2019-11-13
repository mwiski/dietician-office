package pl.mwiski.dieticianoffice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SimpleDieticianDto {

    private final long id;
    private final String name;
    private final String lastName;
    private final String phoneNumber;
    private final String mail;
}
