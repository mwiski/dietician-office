package pl.mwiski.dieticianoffice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AnswerDto {

    private final long id;
    private final String answer;
    private final QuestionDto question;
    private final String addedAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final SimpleDieticianDto dietician;
}
