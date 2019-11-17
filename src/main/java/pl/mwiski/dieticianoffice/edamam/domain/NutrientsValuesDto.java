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
public class NutrientsValuesDto {

    @JsonProperty("ENERC_KCAL")
    private double enercKcal;
    @JsonProperty("PROCNT")
    private double procnt;
    @JsonProperty("FAT")
    private double fat;
    @JsonProperty("CHOCDF")
    private double chocdf;
    @JsonProperty("FIBTG")
    private double fibtg;
}
