package pl.mwiski.dieticianoffice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddressDto {

    private final String city;
    private final String postalCode;
    private final String street;
    private final String buildingNumber;
    private final String apartmentNumber;
}
