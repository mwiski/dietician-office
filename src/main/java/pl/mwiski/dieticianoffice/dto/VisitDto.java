package pl.mwiski.dieticianoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitDto {

    private long id;
    private TermDto term;
    private UserDto user;
    private DieticianDto dietician;
    private boolean happened;
}
