package pl.mwiski.dieticianoffice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @NotNull
    @Column
    private String city;

    @NotNull
    @Column
    private String postalCode;

    @NotNull
    @Column
    private String street;

    @NotNull
    @Column
    private String buildingNumber;

    @Column
    private String apartmentNumber;

    @OneToMany(mappedBy = "address")
    private List<User> users = new ArrayList<>();

    public Address(String city, String postalCode, String street, String buildingNumber, String apartmentNumber) {
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
    }
}
