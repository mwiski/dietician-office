package pl.mwiski.dieticianoffice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    @NotNull
    private String city;

    @NotNull
    private String postalCode;

    @NotNull
    private String street;

    @NotNull
    private String buildingNumber;

    private String apartmentNumber;
}
