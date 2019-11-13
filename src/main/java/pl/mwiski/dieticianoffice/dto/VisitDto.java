package pl.mwiski.dieticianoffice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class VisitDto {

    private final long id;
    private final LocalDateTime dateAndTime;
    private final SimpleUserDto user;
    private final SimpleDieticianDto dietician;
    private final boolean available;
    private final boolean completed;
}
