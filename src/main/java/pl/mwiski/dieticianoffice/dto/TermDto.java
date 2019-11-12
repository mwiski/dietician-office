package pl.mwiski.dieticianoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermDto {

    private long id;
    private LocalDateTime dateAndTime;
    private List<DieticianDto> dieticians = new ArrayList<>();
    private List<VisitDto> visits = new ArrayList<>();
    private boolean available;

    public TermDto(long id, LocalDateTime dateAndTime, List<DieticianDto> dieticians, boolean available) {
        this.id = id;
        this.dateAndTime = dateAndTime;
        this.dieticians = dieticians;
        this.visits = new ArrayList<>();
        this.available = available;
    }
}