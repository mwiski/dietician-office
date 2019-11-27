package pl.mwiski.dieticianoffice.spoonacular.mapper;

import org.junit.Before;
import org.junit.Test;
import pl.mwiski.dieticianoffice.spoonacular.domain.detailedrecipe.DetailedRecipeDto;
import pl.mwiski.dieticianoffice.spoonacular.domain.detailedrecipe.IngredientDto;
import pl.mwiski.dieticianoffice.spoonacular.domain.inner.FoundRecipeDto;
import pl.mwiski.dieticianoffice.spoonacular.domain.inner.FoundResultDto;
import pl.mwiski.dieticianoffice.spoonacular.domain.results.RecipeDto;
import pl.mwiski.dieticianoffice.spoonacular.domain.results.ResultsDto;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class SpoonacularMapperTest {

    private SpoonacularMapper spoonacularMapper;
    private DetailedRecipeDto detailedRecipeDto;
    private FoundRecipeDto foundRecipeDto;
    private ResultsDto resultsDto;
    private FoundResultDto foundResultDto;

    @Before
    public void before() {
        spoonacularMapper = new SpoonacularMapper();
    }

    @Test
    public void shouldMapToFoundRecipeDto() {
        //Given
        detailedRecipeDto = new DetailedRecipeDto(
                true,
                true,
                true,
                true,
                Arrays.asList(new IngredientDto("ingredient")),
                "name",
                1,
                1,
                Arrays.asList("dishType"),
                "instruction"
        );

        foundRecipeDto = new FoundRecipeDto(
                "name",
                true,
                true,
                true,
                true,
                Arrays.asList("ingredient"),
                1,
                1,
                Arrays.asList("dishType"),
                "instruction"
        );

        //When
        FoundRecipeDto result = spoonacularMapper.toFoundRecipeDto(detailedRecipeDto);

        //Then
        assertThat(result).isEqualToComparingFieldByField(foundRecipeDto);
    }

    @Test
    public void shouldMapToFoundResultDtoList() {
        //Given
        resultsDto = new ResultsDto(
                Arrays.asList(new RecipeDto(
                        1,
                        "title",
                        2,
                        3
                ))
        );

        List<FoundResultDto> resultDtos = Arrays.asList(new FoundResultDto(
                1,
                "title",
                2,
                3
                ));

        //When
        List<FoundResultDto> result = spoonacularMapper.toFoundResultDtoList(resultsDto);

        //Then
        assertThat(result).isEqualTo(resultDtos);
    }
}
