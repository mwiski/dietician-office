package pl.mwiski.dieticianoffice.spoonacular.domain.detailedrecipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailedRecipeDto {

    @JsonProperty("vegetarian")
    private boolean vegetarian;
    @JsonProperty("vegan")
    private boolean vegan;
    @JsonProperty("glutenFree")
    private boolean glutenFree;
    @JsonProperty("dairyFree")
    private boolean dairyFree;
    @JsonProperty("extendedIngredients")
    private List<IngredientDto> ingredients;
    @JsonProperty("title")
    private String name;
    @JsonProperty("readyInMinutes")
    private int readyInMinutes;
    @JsonProperty("servings")
    private int servings;
    @JsonProperty("dishTypes")
    private List<String> dishTypes;
    @JsonProperty("instructions")
    private String instructions;
}
