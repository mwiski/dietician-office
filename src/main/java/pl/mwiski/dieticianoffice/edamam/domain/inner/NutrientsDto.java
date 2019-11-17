package pl.mwiski.dieticianoffice.edamam.domain.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutrientsDto {

    private double kcal;
    private double proteins;
    private double fat;
    private double carbohydrates;
    private double fiber;
}
