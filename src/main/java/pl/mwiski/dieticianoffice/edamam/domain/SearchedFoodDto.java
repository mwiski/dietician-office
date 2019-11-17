package pl.mwiski.dieticianoffice.edamam.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchedFoodDto {

    @JsonProperty("text")
    private String text;

    @JsonProperty("parsed")
    private List<ParsedDto> parsedList;
}
