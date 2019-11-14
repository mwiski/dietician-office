package pl.mwiski.dieticianoffice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class VisitDto {

    private final long id;
    private final String dateTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final SimpleUserDto user;
    private final SimpleDieticianDto dietician;
    private final boolean available;
    private final boolean completed;
}
