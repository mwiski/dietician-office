package pl.mwiski.dieticianoffice.edamam.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodDto {

    @JsonProperty("label")
    private String label;
    @JsonProperty("nutrients")
    private NutrientsValuesDto nutrientsValuesDto;
}
