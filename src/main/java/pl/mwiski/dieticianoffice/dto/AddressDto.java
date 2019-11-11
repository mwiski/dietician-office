package pl.mwiski.dieticianoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private long id;
    private String city;
    private String postalCode;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
}
