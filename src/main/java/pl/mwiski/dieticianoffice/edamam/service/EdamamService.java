package pl.mwiski.dieticianoffice.edamam.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mwiski.dieticianoffice.edamam.client.EdamamClient;
import pl.mwiski.dieticianoffice.edamam.domain.inner.FoundFoodDto;
import pl.mwiski.dieticianoffice.edamam.mapper.EdamamMapper;

@Service
@Slf4j
public class EdamamService {

    @Autowired
    private EdamamClient edamamClient;
    @Autowired
    private EdamamMapper edamamMapper;

    public FoundFoodDto getFoodsNutritionalValue(final String foodName) {
        log.info("Getting nutritional values of food found by name: [{}]", foodName);
        return edamamMapper.toFoundFoodDto(edamamClient.getFoundFood(foodName));
    }
}
