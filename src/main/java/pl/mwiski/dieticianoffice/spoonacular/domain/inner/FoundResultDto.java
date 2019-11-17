package pl.mwiski.dieticianoffice.spoonacular.domain.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoundResultDto {

    private int id;
    private String title;
    private int readyInMinutes;
    private int servings;
}
