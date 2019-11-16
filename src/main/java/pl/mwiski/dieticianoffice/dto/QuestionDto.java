package pl.mwiski.dieticianoffice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Data
@RequiredArgsConstructor
public class QuestionDto {

    private final long id;
    private final String question;
    private final String addedAt;
    private final SimpleUserDto user;
    private final List<SimpleDieticianDto> dieticians;
}
