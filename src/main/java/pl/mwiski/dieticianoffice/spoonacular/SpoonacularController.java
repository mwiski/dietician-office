package pl.mwiski.dieticianoffice.spoonacular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mwiski.dieticianoffice.spoonacular.domain.inner.FoundRecipeDto;
import pl.mwiski.dieticianoffice.spoonacular.domain.inner.FoundResultDto;
import pl.mwiski.dieticianoffice.spoonacular.service.SpoonacularService;
import java.util.List;

@RestController
@RequestMapping(SpoonacularController.BASE_API)
public class SpoonacularController {

    static final String BASE_API = "v1/spoonacular";

    @Autowired
    private SpoonacularService spoonacularService;

    @GetMapping("recipes/{query}/${api.key}")
    public List<FoundResultDto> getRecipes(@PathVariable final String query) {
        return spoonacularService.getRecipes(query);
    }

    @GetMapping("recipes/info/{id}/${api.key}")
    public FoundRecipeDto getRecipe(@PathVariable final int id) {
        return spoonacularService.getRecipe(id);
    }
}
