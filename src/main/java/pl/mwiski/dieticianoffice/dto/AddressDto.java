package pl.mwiski.dieticianoffice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@RequiredArgsConstructor
public class AddressDto {

    @NotEmpty
    private final String city;
    @NotEmpty
    private final String postalCode;
    @NotEmpty
    private final String street;
    @NotEmpty
    private final String buildingNumber;
    private final String apartmentNumber;
}
