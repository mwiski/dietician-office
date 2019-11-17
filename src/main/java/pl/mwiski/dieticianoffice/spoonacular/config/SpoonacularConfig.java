package pl.mwiski.dieticianoffice.spoonacular.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SpoonacularConfig {

    @Value("${spoonacular.api.endpoint.prod}")
    private String spoonacularApiEndpoint;

    @Value("${spoonacular.apiKey}")
    private String spoonacularKey;
}
