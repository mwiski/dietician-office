package pl.mwiski.dieticianoffice.spoonacular.domain.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoundRecipeDto {

    private String name;
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;
    private List<String> ingredients;
    private int readyInMinutes;
    private int servings;
    private List<String> dishTypes;
    private String instructions;
}
