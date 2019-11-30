package pl.mwiski.dieticianoffice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    @NotEmpty
    private String postalCode;

    @NotNull
    @NotEmpty
    private String street;

    @NotNull
    @NotEmpty
    private String buildingNumber;

    private String apartmentNumber;
}
