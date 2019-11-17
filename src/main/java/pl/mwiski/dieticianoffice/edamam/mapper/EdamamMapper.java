package pl.mwiski.dieticianoffice.edamam.mapper;

import org.springframework.stereotype.Component;
import pl.mwiski.dieticianoffice.edamam.domain.SearchedFoodDto;
import pl.mwiski.dieticianoffice.edamam.domain.NutrientsValuesDto;
import pl.mwiski.dieticianoffice.edamam.domain.inner.FoundFoodDto;
import pl.mwiski.dieticianoffice.edamam.domain.inner.NutrientsDto;

@Component
public class EdamamMapper {

    public FoundFoodDto toFoundFoodDto(final SearchedFoodDto searchedFoodDto) {
        if (searchedFoodDto == null) return new FoundFoodDto();
        return new FoundFoodDto(
                searchedFoodDto.getText(),
                searchedFoodDto.getParsedList().get(0).getFood().getLabel(),
                toNutrientsDto(searchedFoodDto.getParsedList().get(0).getFood().getNutrientsValuesDto())
        );
    }

    private NutrientsDto toNutrientsDto(final NutrientsValuesDto nutrientsValuesDto) {
        if (nutrientsValuesDto == null) return new NutrientsDto();
        return new NutrientsDto(
                nutrientsValuesDto.getEnercKcal(),
                nutrientsValuesDto.getProcnt(),
                nutrientsValuesDto.getFat(),
                nutrientsValuesDto.getChocdf(),
                nutrientsValuesDto.getFibtg()
        );
    }
}
