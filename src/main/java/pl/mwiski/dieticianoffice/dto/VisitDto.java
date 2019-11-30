package pl.mwiski.dieticianoffice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class VisitDto {

    private final long id;
    @NotEmpty
    private final String dateTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final SimpleUserDto user;
    @NotNull
    private final SimpleDieticianDto dietician;
    private final boolean available;
    private final boolean completed;
}
