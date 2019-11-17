package pl.mwiski.dieticianoffice.spoonacular.mapper;

import org.springframework.stereotype.Component;
import pl.mwiski.dieticianoffice.spoonacular.domain.detailedrecipe.DetailedRecipeDto;
import pl.mwiski.dieticianoffice.spoonacular.domain.detailedrecipe.IngredientDto;
import pl.mwiski.dieticianoffice.spoonacular.domain.inner.FoundRecipeDto;
import pl.mwiski.dieticianoffice.spoonacular.domain.inner.FoundResultDto;
import pl.mwiski.dieticianoffice.spoonacular.domain.results.ResultsDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpoonacularMapper {

    public FoundRecipeDto toFoundRecipeDto(final DetailedRecipeDto detailedRecipeDto) {
        if (detailedRecipeDto == null) return new FoundRecipeDto();
        return new FoundRecipeDto(
                detailedRecipeDto.getName(),
                detailedRecipeDto.isVegetarian(),
                detailedRecipeDto.isVegan(),
                detailedRecipeDto.isGlutenFree(),
                detailedRecipeDto.isDairyFree(),
                toIngredientsList(detailedRecipeDto.getIngredients()),
                detailedRecipeDto.getReadyInMinutes(),
                detailedRecipeDto.getServings(),
                detailedRecipeDto.getDishTypes(),
                detailedRecipeDto.getInstructions()
        );
    }

    private List<String> toIngredientsList(final List<IngredientDto> ingredientDtos) {
        if (ingredientDtos == null) return new ArrayList<>();
        return ingredientDtos.stream()
                .map(IngredientDto::getIngredient)
                .collect(Collectors.toList());
    }

    public List<FoundResultDto> toFoundResultDtoList(final ResultsDto resultsDto) {
        if (resultsDto == null) return new ArrayList<>();
        return resultsDto.getRecipes().stream()
                .map(recipeDto -> new FoundResultDto(
                        recipeDto.getId(),
                        recipeDto.getTitle(),
                        recipeDto.getReadyInMinutes(),
                        recipeDto.getServings()))
                .collect(Collectors.toList());
    }
}
