package pl.mwiski.dieticianoffice.spoonacular.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.spoonacular.client.SpoonacularClient;
import pl.mwiski.dieticianoffice.spoonacular.domain.inner.FoundRecipeDto;
import pl.mwiski.dieticianoffice.spoonacular.domain.inner.FoundResultDto;
import pl.mwiski.dieticianoffice.spoonacular.mapper.SpoonacularMapper;
import java.util.List;

@Service
@Slf4j
public class SpoonacularService {

    @Autowired
    private SpoonacularClient spoonacularClient;
    @Autowired
    private SpoonacularMapper spoonacularMapper;

    public List<FoundResultDto> getRecipes(final String query) {
        log.info("Getting list of recipes for query [{}]", query);
        return spoonacularMapper.toFoundResultDtoList(spoonacularClient.getRecipes(query));
    }

    public FoundRecipeDto getRecipe(final int id) {
        log.info("Getting detailed information about recipe with ID [{}]", id);
        return spoonacularMapper.toFoundRecipeDto(spoonacularClient.getDetailedRecipe(id));
    }
}
