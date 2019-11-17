package pl.mwiski.dieticianoffice.edamam.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.mwiski.dieticianoffice.edamam.config.EdamamConfig;
import pl.mwiski.dieticianoffice.edamam.domain.SearchedFoodDto;
import java.net.URI;
import java.util.Optional;

@Component
@Slf4j
public class EdamamClient {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EdamamConfig edamamConfig;

    public SearchedFoodDto getFoundFood(final String foodName) {
        try {
            SearchedFoodDto searchedFoodDto = restTemplate.getForObject(getUri(foodName), SearchedFoodDto.class);
            return Optional.ofNullable(searchedFoodDto).orElse(new SearchedFoodDto());
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return new SearchedFoodDto();
        }
    }

    private URI getUri(final String foodName) {
        return UriComponentsBuilder.fromHttpUrl(edamamConfig.getEdamamApiEndpoint())
                .queryParam("ingr", foodName)
                .queryParam("app_id", edamamConfig.getEdamamId())
                .queryParam("app_key", edamamConfig.getEdamamKey()).build().encode().toUri();
    }
}
