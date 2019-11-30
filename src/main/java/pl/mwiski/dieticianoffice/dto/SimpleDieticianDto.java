package pl.mwiski.dieticianoffice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.mwiski.dieticianoffice.entity.enums.RoleType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class SimpleDieticianDto {

    private final long id;
    @NotEmpty
    private final String name;
    @NotEmpty
    private final String lastName;
    @NotEmpty
    private final String dietician;
    @NotNull
    private final RoleType roleType;
    @NotEmpty
    private final String phoneNumber;
    @NotEmpty
    private final String mail;
}
