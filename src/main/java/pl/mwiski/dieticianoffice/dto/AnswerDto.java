package pl.mwiski.dieticianoffice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class AnswerDto {

    private final long id;
    @NotEmpty
    private final String answer;
    @NotNull
    private final QuestionDto question;
    private final String addedAt;
    @NotNull
    private final SimpleDieticianDto dietician;
}
