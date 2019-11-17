package pl.mwiski.dieticianoffice.edamam.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EdamamConfig {

    @Value("${edamam.api.endpoint.prod}")
    private String edamamApiEndpoint;

    @Value("${edamam.app.id}")
    private String edamamId;

    @Value("${edamam.app.key}")
    private String edamamKey;

}
