package pl.mwiski.dieticianoffice.edamam.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.mwiski.dieticianoffice.edamam.domain.inner.FoundFoodDto;
import pl.mwiski.dieticianoffice.edamam.domain.inner.NutrientsDto;
import pl.mwiski.dieticianoffice.edamam.service.EdamamService;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EdamamController.class)
public class EdamamControllerTest {

    private String key =
    private FoundFoodDto foundFoodDto;
    private String foodName;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EdamamService edamamService;

    @Before
    public void before() {
        foodName = "foodName";
        foundFoodDto = new FoundFoodDto(
                "text",
                "label",
                new NutrientsDto(1.0, 2.0, 3.0, 4.0, 5.0)
        );
    }

    @Test
    public void shouldReturnAllAnswers() throws Exception {
        //Given
        when(edamamService.getFoodsNutritionalValue(foodName)).thenReturn(foundFoodDto);

        //When & Then
        mockMvc.perform(get("/v1/edamam/foods/" + foodName + "/" + key).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchedParam", is("text")))
                .andExpect(jsonPath("$.found", is("label")))
                .andExpect(jsonPath("$.nutrients.kcal", is(1.0)))
                .andExpect(jsonPath("$.nutrients.proteins", is(2.0)))
                .andExpect(jsonPath("$.nutrients.fat", is(3.0)))
                .andExpect(jsonPath("$.nutrients.carbohydrates", is(4.0)))
                .andExpect(jsonPath("$.nutrients.fiber", is(5.0)));
    }
}
