package pl.mwiski.dieticianoffice.spoonacular.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.mwiski.dieticianoffice.spoonacular.config.SpoonacularConfig;
import pl.mwiski.dieticianoffice.spoonacular.domain.detailedrecipe.DetailedRecipeDto;
import pl.mwiski.dieticianoffice.spoonacular.domain.results.ResultsDto;
import java.net.URI;
import java.util.Optional;

@Component
@Slf4j
public class SpoonacularClient {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SpoonacularConfig spoonacularConfig;

    public ResultsDto getRecipes(final String query) {
        try {
            ResultsDto results = restTemplate.getForObject(getRecipesUri(query), ResultsDto.class);
            return Optional.ofNullable(results).orElse(new ResultsDto());
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return new ResultsDto();
        }
    }

    private URI getRecipesUri(final String query) {
        return UriComponentsBuilder.fromHttpUrl(spoonacularConfig.getSpoonacularApiEndpoint() + "/search")
                .queryParam("number", 5)
                .queryParam("query", query)
                .queryParam("apiKey", spoonacularConfig.getSpoonacularKey()).build().encode().toUri();
    }

    public DetailedRecipeDto getDetailedRecipe(final int id) {
        try {
            DetailedRecipeDto recipe = restTemplate.getForObject(getDetailedRecipeUri(id), DetailedRecipeDto.class);
            return Optional.ofNullable(recipe).orElse(new DetailedRecipeDto());
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return new DetailedRecipeDto();
        }
    }

    private URI getDetailedRecipeUri(final int id) {
        return UriComponentsBuilder.fromHttpUrl(spoonacularConfig.getSpoonacularApiEndpoint())
                .path("/" + id)
                .path("/information")
                .queryParam("apiKey", spoonacularConfig.getSpoonacularKey()).build().encode().toUri();
    }
}
