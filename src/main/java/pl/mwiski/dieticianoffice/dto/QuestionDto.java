package pl.mwiski.dieticianoffice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class QuestionDto {

    private final long id;
    @NotEmpty
    private final String question;
    private final String addedAt;
    @NotNull
    private final SimpleUserDto user;
}
