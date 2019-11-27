package pl.mwiski.dieticianoffice.edamam.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mwiski.dieticianoffice.edamam.client.EdamamClient;
import pl.mwiski.dieticianoffice.edamam.domain.FoodDto;
import pl.mwiski.dieticianoffice.edamam.domain.NutrientsValuesDto;
import pl.mwiski.dieticianoffice.edamam.domain.ParsedDto;
import pl.mwiski.dieticianoffice.edamam.domain.SearchedFoodDto;
import pl.mwiski.dieticianoffice.edamam.domain.inner.FoundFoodDto;
import pl.mwiski.dieticianoffice.edamam.domain.inner.NutrientsDto;
import pl.mwiski.dieticianoffice.edamam.mapper.EdamamMapper;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EdamamServiceTest {

    @InjectMocks
    private EdamamService edamamService;
    @Mock
    private EdamamClient edamamClient;
    @Mock
    private EdamamMapper edamamMapper;

    private SearchedFoodDto searchedFoodDto;
    private FoundFoodDto foundFoodDto;

    @Before
    public void before() {
        searchedFoodDto = new SearchedFoodDto(
                "text",
                Arrays.asList(new ParsedDto(
                        new FoodDto(
                                "label",
                                new NutrientsValuesDto(1.0, 2.0, 3.0, 4.0, 5.0)
                        ))));
        foundFoodDto = new FoundFoodDto(
                "text",
                "label",
                new NutrientsDto(1.0, 2.0, 3.0, 4.0, 5.0)
        );
    }

    @Test
    public void shouldGetFoodsNutritionalValue() {
        //Given
        String foodName = "foodName";
        when(edamamClient.getFoundFood(foodName)).thenReturn(searchedFoodDto);
        when(edamamMapper.toFoundFoodDto(searchedFoodDto)).thenReturn(foundFoodDto);

        //When
        FoundFoodDto result = edamamService.getFoodsNutritionalValue(foodName);

        //Then
        assertThat(result).isEqualTo(foundFoodDto);
    }
}
