package pl.mwiski.dieticianoffice.edamam.mapper;

import org.junit.Before;
import org.junit.Test;
import pl.mwiski.dieticianoffice.edamam.domain.FoodDto;
import pl.mwiski.dieticianoffice.edamam.domain.NutrientsValuesDto;
import pl.mwiski.dieticianoffice.edamam.domain.ParsedDto;
import pl.mwiski.dieticianoffice.edamam.domain.SearchedFoodDto;
import pl.mwiski.dieticianoffice.edamam.domain.inner.FoundFoodDto;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

public class EdamamMapperTest {

    private EdamamMapper edamamMapper;
    private FoundFoodDto foundFoodDto;
    private SearchedFoodDto searchedFoodDto;

    @Before
    public void before() {
        edamamMapper = new EdamamMapper();
    }

    @Test
    public void shouldMapToFoundFoodDto() {
        //Given
        searchedFoodDto = new SearchedFoodDto(
                "text",
                Arrays.asList(new ParsedDto(
                        new FoodDto(
                                "label",
                                new NutrientsValuesDto(1.0, 2.0, 3.0, 4.0, 5.0)
                        ))));

        //When
        foundFoodDto = edamamMapper.toFoundFoodDto(searchedFoodDto);

        //Then
        assertThat(foundFoodDto.getSearchedParam()).isEqualTo(searchedFoodDto.getText());
        assertThat(foundFoodDto.getFound()).isEqualTo(searchedFoodDto.getParsedList().get(0).getFood().getLabel());
        assertThat(foundFoodDto.getNutrients().getKcal()).isEqualTo(searchedFoodDto.getParsedList().get(0).getFood().getNutrientsValuesDto().getEnercKcal());
        assertThat(foundFoodDto.getNutrients().getProteins()).isEqualTo(searchedFoodDto.getParsedList().get(0).getFood().getNutrientsValuesDto().getProcnt());
        assertThat(foundFoodDto.getNutrients().getFat()).isEqualTo(searchedFoodDto.getParsedList().get(0).getFood().getNutrientsValuesDto().getFat());
        assertThat(foundFoodDto.getNutrients().getCarbohydrates()).isEqualTo(searchedFoodDto.getParsedList().get(0).getFood().getNutrientsValuesDto().getChocdf());
        assertThat(foundFoodDto.getNutrients().getFiber()).isEqualTo(searchedFoodDto.getParsedList().get(0).getFood().getNutrientsValuesDto().getFibtg());
    }
}
