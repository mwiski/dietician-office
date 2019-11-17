package pl.mwiski.dieticianoffice.edamam.domain.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoundFoodDto {

    private String searchedParam;
    private String found;
    private NutrientsDto nutrients;
}
