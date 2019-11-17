package pl.mwiski.dieticianoffice.edamam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mwiski.dieticianoffice.edamam.domain.inner.FoundFoodDto;
import pl.mwiski.dieticianoffice.edamam.service.EdamamService;

@RestController
@RequestMapping(EdmamController.BASE_API)
public class EdmamController {

    static final String BASE_API = "v1/edamam";

    @Autowired
    private EdamamService edamamService;

    @GetMapping("foods/{foodName}")
    public FoundFoodDto getFoodsNutritionalValue(@PathVariable final String foodName) {
        return edamamService.getFoodsNutritionalValue(foodName);
    }
}
